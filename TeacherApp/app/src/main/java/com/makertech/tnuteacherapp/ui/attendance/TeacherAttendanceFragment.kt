package com.makertech.tnuteacherapp.ui.attendance

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.makertech.tnuteacherapp.R
import com.makertech.tnuteacherapp.data.remote.request.AttendancePerStudent
import com.makertech.tnuteacherapp.data.remote.response.Post
import com.makertech.tnuteacherapp.databinding.TeacherAttendanceFragmentBinding
import com.makertech.tnuteacherapp.other.Status
import com.makertech.tnuteacherapp.ui.BaseFragment
import com.makertech.tnuteacherapp.ui.posts.PostAdapter
import com.markertech.data.request.StudentPerSemAndDept
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class TeacherAttendanceFragment:BaseFragment<TeacherAttendanceFragmentBinding>(R.layout.teacher_attendance_fragment, TeacherAttendanceFragmentBinding::inflate) {
    private val viewModel: TakeAttendanceViewModel by viewModels()
    lateinit var teacherAttendanceAdapter: TeacherAttendanceAdapter

    override fun initViews() {
        val departments = ArrayAdapter(requireContext(),R.layout.dropdown_item_attendance,resources.getStringArray(R.array.Departments))
        val semesters = ArrayAdapter(requireContext(),R.layout.dropdown_item_attendance,resources.getStringArray(R.array.Semester))
        val subjects = ArrayAdapter(requireContext(),R.layout.dropdown_item_attendance,resources.getStringArray(R.array.Subjects))


        binding.edtSelectDept.setAdapter(departments)
        binding.txtSemesterForAttendance.setAdapter(semesters)
        binding.txtSubjectForAttendance.setAdapter(subjects)
        binding.btnGetStudentsForAttendance.setOnClickListener {
            viewModel.getAttendanceList(StudentPerSemAndDept(binding.edtSelectDept.text.toString(),binding.txtSemesterForAttendance.text.toString()))
        }

        binding.btnUploadAttendance.setOnClickListener {
            viewModel.giveAttendance(binding.txtSubjectForAttendance.text.toString(),teacherAttendanceAdapter.getAttendance())
        }

    }

    override fun observe() {
        viewModel.getAttendanceStatus.observe(this, androidx.lifecycle.Observer {result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS ->{

                        binding.attendanceSheetRecyclerView.layoutManager = LinearLayoutManager(context)
                        teacherAttendanceAdapter = TeacherAttendanceAdapter(result.data as ArrayList<AttendancePerStudent>)
                        binding.attendanceSheetRecyclerView.adapter = teacherAttendanceAdapter
                        Timber.log(1,result.data.toString())
                    }
                    Status.ERROR ->{
                        makeToast("Error"+result.data)

                    }
                    Status.LOADING ->{
                        Timber.log(1,"loading"+result.data)
                    }
                }
            }
        })

        viewModel.giveAttendanceStatus.observe(this) {result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS->{
                        binding.txtSubjectForAttendance.text = null
                        binding.txtSemesterForAttendance.text = null
                        binding.edtSelectDept.text = null
                        teacherAttendanceAdapter.emptyData()
                        teacherAttendanceAdapter.notifyDataSetChanged()
                    }
                    Status.ERROR->{
                        makeToast("Error Occurred "+ (result.data?.message ?: " "))
                    }
                    Status.LOADING->{

                    }
                }
            }


        }
    }
}