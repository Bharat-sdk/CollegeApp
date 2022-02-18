package com.makertech.collegeapp.ui.posts

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.makertech.collegeapp.R
import com.makertech.collegeapp.data.remote.response.Post
import com.makertech.collegeapp.databinding.FragmentStudentNoticeBinding
import com.makertech.collegeapp.other.Status
import com.makertech.collegeapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PostFragment:BaseFragment<FragmentStudentNoticeBinding>(R.layout.fragment_student_notice,FragmentStudentNoticeBinding::inflate) {
    private val viewModel: PostViewModel by viewModels()
    override fun initViews() {
        viewModel.getAllPosts()
    }

    override fun observe() {
        viewModel.postStatus.observe(this, Observer {result->
            result?.let {
                when(result.status)
                {
                    Status.SUCCESS ->{
                        val linearLayoutManager = LinearLayoutManager(context)
                        linearLayoutManager.reverseLayout = true
                        linearLayoutManager.stackFromEnd = true
                        binding.noticeRecyclerView.layoutManager = linearLayoutManager
                        binding.noticeRecyclerView.adapter = PostAdapter(result.data as ArrayList<Post>)

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
    }

}