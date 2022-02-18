package com.makertech.collegeapp.ui.studentTimetable

import androidx.navigation.fragment.findNavController
import com.makertech.collegeapp.R
import com.makertech.collegeapp.databinding.FragmentWorkAttendanceWeekdaysBinding
import com.makertech.collegeapp.ui.BaseFragment

class WeekdayFragment :
    BaseFragment<FragmentWorkAttendanceWeekdaysBinding>(R.layout.fragment_work_attendance_weekdays,
        FragmentWorkAttendanceWeekdaysBinding::inflate) {
    override fun initViews() {

        binding.cardMonday.setOnClickListener {
            val action =
                WeekdayFragmentDirections.actionWeekdayFragmentToStudentTimeTableFragmnent("Monday")
            findNavController().navigate(action)
        }
        binding.cardTuesday.setOnClickListener {
            val action =
                WeekdayFragmentDirections.actionWeekdayFragmentToStudentTimeTableFragmnent("Tuesday")
            findNavController().navigate(action)
        }
        binding.cardWednesday.setOnClickListener {
            val action =
                WeekdayFragmentDirections.actionWeekdayFragmentToStudentTimeTableFragmnent("Wednesday")
            findNavController().navigate(action)
        }
        binding.cardThursday.setOnClickListener {
            val action =
                WeekdayFragmentDirections.actionWeekdayFragmentToStudentTimeTableFragmnent("Thursday")
            findNavController().navigate(action)
        }
        binding.cardFriday.setOnClickListener {
            val action =
                WeekdayFragmentDirections.actionWeekdayFragmentToStudentTimeTableFragmnent("Friday")
            findNavController().navigate(action)
        }

    }
}