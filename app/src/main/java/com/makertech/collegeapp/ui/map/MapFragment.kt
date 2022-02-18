package com.makertech.collegeapp.ui.map

import android.content.SharedPreferences
import com.makertech.collegeapp.R
import com.makertech.collegeapp.databinding.FragmentStudentProfileBinding
import com.makertech.collegeapp.other.AppConstants.KEY_DEPARTMENT
import com.makertech.collegeapp.other.AppConstants.KEY_LOGGED_IN_EMAIL
import com.makertech.collegeapp.other.AppConstants.KEY_NAME
import com.makertech.collegeapp.other.AppConstants.KEY_SEMESTER
import com.makertech.collegeapp.other.AppConstants.KEY_UID
import com.makertech.collegeapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint

class MapFragment : BaseFragment<FragmentStudentProfileBinding>(
    R.layout.fragment_student_profile,
    FragmentStudentProfileBinding::inflate) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun initViews() {
        binding.profileName.text = sharedPreferences.getString(KEY_NAME,"")
        binding.profileEmail.text = sharedPreferences.getString(KEY_LOGGED_IN_EMAIL,"")
        binding.profileSemester.text = sharedPreferences.getString(KEY_SEMESTER,"")
        binding.profileDepartment.text = sharedPreferences.getString(KEY_DEPARTMENT,"")
        binding.profileUid.text  = sharedPreferences.getString(KEY_UID,"")

    }

}