package com.makertech.tnuteacherapp.ui.posts

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.makertech.tnuteacherapp.R
import com.makertech.tnuteacherapp.data.remote.response.Post
import com.makertech.tnuteacherapp.databinding.FragmentTeacherNoticeBinding
import com.makertech.tnuteacherapp.other.Status
import com.makertech.tnuteacherapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PostFragment: BaseFragment<FragmentTeacherNoticeBinding>(R.layout.fragment_teacher_notice,FragmentTeacherNoticeBinding::inflate) {
    private val viewModel: PostViewModel by viewModels()
    override fun initViews() {
        viewModel.getAllPosts()
        binding.btnAddNotice.setOnClickListener {
            findNavController().navigate(R.id.addPostFragment)
        }
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