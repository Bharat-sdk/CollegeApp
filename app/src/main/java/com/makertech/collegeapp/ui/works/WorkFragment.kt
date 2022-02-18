package com.makertech.collegeapp.ui.works

import androidx.navigation.fragment.findNavController
import com.makertech.collegeapp.R
import com.makertech.collegeapp.databinding.FragmentStudentWorkBinding
import com.makertech.collegeapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkFragment :BaseFragment<FragmentStudentWorkBinding>(R.layout.fragment_student_work,FragmentStudentWorkBinding::inflate) {
    override fun initViews() {
        binding.btnAttendance.setOnClickListener {
            findNavController().navigate(R.id.studentAttendanceFragment)
        }

        binding.btnTimetable.setOnClickListener {
            findNavController().navigate(R.id.weekdayFragment)
        }


    }
}
