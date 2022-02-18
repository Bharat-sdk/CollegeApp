package com.makertech.tnuteacherapp.ui.aboutUs


import com.makertech.tnuteacherapp.R
import com.makertech.tnuteacherapp.databinding.FragmentAboutusBinding
import com.makertech.tnuteacherapp.ui.BaseFragment

class AboutUsFragment : BaseFragment<FragmentAboutusBinding>(
    R.layout.fragment_aboutus,
    FragmentAboutusBinding::inflate) {
    override fun initViews() {
        binding.webAboutUs.loadUrl("https://www.tnu.in")
    }

}