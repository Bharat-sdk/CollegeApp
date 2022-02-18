package com.makertech.collegeapp.ui.studentTimetable

import android.content.SharedPreferences
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.makertech.collegeapp.R
import com.makertech.collegeapp.data.remote.response.TimeTableResponse
import com.makertech.collegeapp.databinding.ActivityWorkAttendanceDailysubjectsBinding
import com.makertech.collegeapp.other.AppConstants.KEY_DEPARTMENT
import com.makertech.collegeapp.other.AppConstants.KEY_SEMESTER
import com.makertech.collegeapp.other.Status
import com.makertech.collegeapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentTimeTableFragmnent:BaseFragment<ActivityWorkAttendanceDailysubjectsBinding>(R.layout.activity_work_attendance_dailysubjects,
    ActivityWorkAttendanceDailysubjectsBinding::inflate) {

    val args:StudentTimeTableFragmnentArgs by navArgs()
    private val viewModel:StudentTimeTableViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun initViews() {

        val dayOfWeek = args.dayOfWeek
        val department = sharedPreferences.getString(KEY_DEPARTMENT,"")
        val semester = sharedPreferences.getString(KEY_SEMESTER,"")
        if (department != null && semester != null) {
                viewModel.getTimetable(dayOfWeek,department,semester)

        }
        else{
            makeToast("Department and sem is null")
        }
    }

    override fun observe() {
        viewModel.timetable.observe(this, Observer {result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS ->{
                        val studentTimetableAdapter = StudentTimetableAdapter(result.data as ArrayList<TimeTableResponse>)
                        binding.dailyroutineRecyclerView.layoutManager = LinearLayoutManager(context)
                        binding.dailyroutineRecyclerView.adapter = studentTimetableAdapter

                    }
                    Status.ERROR ->{
                        makeToast("Error"+ result.message)
                    }
                    Status.LOADING ->{

                    }
                }
            }
        })
    }
}