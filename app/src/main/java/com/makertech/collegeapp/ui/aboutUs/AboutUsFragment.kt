package com.makertech.collegeapp.ui.aboutUs

import com.makertech.collegeapp.R
import com.makertech.collegeapp.databinding.FragmentAboutusBinding
import com.makertech.collegeapp.ui.BaseFragment

class AboutUsFragment : BaseFragment<FragmentAboutusBinding>(
    R.layout.fragment_aboutus,
    FragmentAboutusBinding::inflate) {
    override fun initViews() {
        binding.webAboutUs.loadUrl("https://www.tnu.in")

    }

}