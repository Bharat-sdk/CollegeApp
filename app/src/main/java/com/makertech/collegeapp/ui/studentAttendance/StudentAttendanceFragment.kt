package com.makertech.collegeapp.ui.studentAttendance

import android.content.SharedPreferences
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.makertech.collegeapp.R
import com.makertech.collegeapp.data.remote.request.AttendancePerSubjectRequest
import com.makertech.collegeapp.databinding.FragmentStudentAttendanceBinding
import com.makertech.collegeapp.other.AppConstants.KEY_UID
import com.makertech.collegeapp.other.Status
import com.makertech.collegeapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StudentAttendanceFragment:BaseFragment<FragmentStudentAttendanceBinding>(R.layout.fragment_student_attendance,
    FragmentStudentAttendanceBinding::inflate) {
    private val viewModel : StudentAttendanceViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun initViews() {

        binding.btnGetAttendance.setOnClickListener {
            val subject = binding.txtSubjectForAttendance.text.toString()
            val email = sharedPreferences.getString(KEY_UID,"")?:""
            viewModel.getAttendanceOfStudent(AttendancePerSubjectRequest(email,subject))
        }
    }

    override fun onResume() {
        super.onResume()
        val array = arrayListOf<String>("DBMS","Automata","Network Security","JAVA","Python")
        val arrayAdapter= ArrayAdapter(requireContext(),R.layout.dropdown_item_attendance,array)
        binding.txtSubjectForAttendance.setAdapter(arrayAdapter)
    }

   override fun observe() {
        viewModel.attendance.observe(this, Observer {result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS ->{

                        val attendanceResponse = result.data
                       binding.txtPresentValue.text = attendanceResponse?.present.toString()
                        binding.txtAbsentValue.text = attendanceResponse?.absent.toString()
                        binding.loadingAnim.visibility = View.INVISIBLE
                        binding.btnGetAttendance.visibility = View.VISIBLE



                    }
                    Status.ERROR ->{
                        binding.loadingAnim.visibility = View.INVISIBLE
                        binding.btnGetAttendance.visibility = View.VISIBLE
                        makeToast("Error"+result.data)

                    }
                    Status.LOADING ->{
                        binding.loadingAnim.visibility = View.VISIBLE
                        binding.btnGetAttendance.visibility = View.INVISIBLE
                    }
                }
            }
        })
    }
}