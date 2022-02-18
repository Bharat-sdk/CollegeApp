package com.makertech.tnuteacherapp.ui.posts

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.makertech.tnuteacherapp.R
import com.makertech.tnuteacherapp.data.remote.response.Post
import com.makertech.tnuteacherapp.databinding.FragmentAddNoticeBinding
import com.makertech.tnuteacherapp.other.AppConstants.KEY_LOGGED_IN_EMAIL
import com.makertech.tnuteacherapp.other.AppConstants.KEY_NAME
import com.makertech.tnuteacherapp.other.Status
import com.makertech.tnuteacherapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddPostFragment: BaseFragment<FragmentAddNoticeBinding>(R.layout.fragment_add_notice,FragmentAddNoticeBinding::inflate) {
    private val viewModel: PostViewModel by viewModels()
    private var imageUrl: String = ""

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    override fun initViews() {
        val email = sharedPreferences.getString(KEY_NAME,"")?:""
        binding.btnUploadNotice.setOnClickListener {
            val title = binding.edtNoticeTitle.text.toString()
            if (title.isEmpty()  )
            {
                makeToast("Fill All The Fields")

            }
            if (imageUrl.isNotEmpty())
            {
                val post = Post(email, imageUrl, title)
                viewModel.insertPost(post)
            }
            else
            {
                makeToast("Select an Image to Post")
            }
        }

        binding.imgUploadPostImage.setOnClickListener {
            ImagePicker.with(this)
                .start()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val fileUri = data?.data!!
                viewModel.uploadPostImage(fileUri)
                binding.imgUploadPostImage.setImageURI(fileUri)
            }
            ImagePicker.RESULT_ERROR -> {
                makeToast(ImagePicker.getError(data))
            }
            else -> {
                makeToast("Task Cancelled")
            }
        }
    }

    override fun observe() {
        viewModel.uploadImage.observe(this, Observer { result ->
            result?.let {
                when (result.status) {
                    Status.SUCCESS -> {
                        imageUrl = result.data.toString()

                    }
                    Status.ERROR -> {
                        makeToast("Error" + result.data)

                    }
                    Status.LOADING -> {

                    }
                }
            }
        })

        viewModel.addPost.observe(this, Observer { result ->
            result?.let {
                when (result.status) {
                    Status.SUCCESS -> {
                        findNavController().navigate(R.id.postFragment)

                    }
                    Status.ERROR -> {
                        findNavController().navigate(R.id.postFragment)

                    }
                    Status.LOADING -> {

                    }
                }
            }
        })


    }
}