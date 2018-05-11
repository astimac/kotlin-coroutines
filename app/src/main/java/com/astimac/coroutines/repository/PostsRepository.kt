package com.astimac.coroutines.repository

import com.astimac.coroutines.data.Post

interface PostsRepository {

    suspend fun getPosts(): List<Post>
}