package com.astimac.coroutines.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.astimac.coroutines.R
import com.astimac.coroutines.data.Post

class PostsAdapter: RecyclerView.Adapter<PostViewHolder>() {

    private val dataSource = mutableListOf<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun getItemCount() = dataSource.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(dataSource[position])
    }

    fun setData(posts: List<Post>) {
        dataSource.clear()
        dataSource.addAll(posts)
        notifyDataSetChanged()
    }
}


