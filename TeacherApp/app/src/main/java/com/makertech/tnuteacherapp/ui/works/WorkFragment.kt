package com.makertech.tnuteacherapp.ui.works

import androidx.navigation.fragment.findNavController
import com.makertech.tnuteacherapp.R
import com.makertech.tnuteacherapp.databinding.FragmentTeacherWorkBinding
import com.makertech.tnuteacherapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkFragment : BaseFragment<FragmentTeacherWorkBinding>(R.layout.fragment_teacher_work,FragmentTeacherWorkBinding::inflate) {
    override fun initViews() {
        binding.btnAttendance.setOnClickListener {
           findNavController().navigate(R.id.teacherAttendanceFragment)
        }





    }
}
