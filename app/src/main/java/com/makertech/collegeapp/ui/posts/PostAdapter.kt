package com.makertech.collegeapp.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makertech.collegeapp.R
import com.makertech.collegeapp.data.remote.response.Post
import com.makertech.collegeapp.databinding.ItemNoticeBinding
import com.squareup.picasso.Picasso

class PostAdapter(var postList: ArrayList<Post>):
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        with(holder){
            with(postList[position]){
                binding.itemNoticeNoticeTitle.text = this.postTitle
                binding.itemNoticeOwnerName.text = this.postOwner
                Picasso.get().load(this.postImage)
                    .error(R.drawable.tnu_logo_splash_screen)
                    .into(binding.noticeImage)
            }
        }
    }

    override fun getItemCount(): Int {
       return postList.size
    }


    inner class PostViewHolder(val binding:ItemNoticeBinding):RecyclerView.ViewHolder(binding.root)
}