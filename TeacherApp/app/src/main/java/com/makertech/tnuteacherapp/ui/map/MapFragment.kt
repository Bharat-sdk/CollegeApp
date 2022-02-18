package com.makertech.tnuteacherapp.ui.map


import android.content.SharedPreferences
import com.makertech.tnuteacherapp.R
import com.makertech.tnuteacherapp.databinding.TeacherProfileFragmentBinding
import com.makertech.tnuteacherapp.other.AppConstants.KEY_LOGGED_IN_EMAIL
import com.makertech.tnuteacherapp.other.AppConstants.KEY_NAME
import com.makertech.tnuteacherapp.other.AppConstants.KEY_SUBJECT
import com.makertech.tnuteacherapp.other.AppConstants.KEY_UID
import com.makertech.tnuteacherapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MapFragment : BaseFragment<TeacherProfileFragmentBinding>(
    R.layout.teacher_profile_fragment,
    TeacherProfileFragmentBinding::inflate) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun initViews() {

        binding.profileName.text = sharedPreferences.getString(KEY_NAME,"")
        binding.profileEmail.text = sharedPreferences.getString(KEY_LOGGED_IN_EMAIL,"")
        binding.profileSubject.text = sharedPreferences.getString(KEY_SUBJECT,"")
        binding.profileUid.text  = sharedPreferences.getString(KEY_UID,"")

    }

}