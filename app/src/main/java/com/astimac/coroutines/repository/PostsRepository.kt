package com.astimac.coroutines.repository

import com.astimac.coroutines.api.PostsService
import com.astimac.coroutines.data.Post
import com.astimac.coroutines.db.PostsDao
import com.astimac.coroutines.utils.NetworkManager

class PostsRepository(private val api: PostsService,
                      private val dao: PostsDao,
                      private val networkManager: NetworkManager) {

    suspend fun getPosts(): List<Post> = if (networkManager.isConnected) {
        val posts = api.getPosts().await()
        dao.deleteAll()
        dao.insertAll(posts)
        posts
    } else {
        dao.selectAll()
    }
}