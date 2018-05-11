package com.astimac.coroutines.ui.main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.astimac.coroutines.R
import com.astimac.coroutines.data.Post

class PostViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var titleLabel: TextView = itemView.findViewById(R.id.post_title_label)
    private var bodyLabel: TextView = itemView.findViewById(R.id.post_body_label)

    fun bind(post: Post) {
        titleLabel.text = post.title
        bodyLabel.text = post.body
    }
}