package com.astimac.coroutines.repository

import com.astimac.coroutines.api.PostsService
import com.astimac.coroutines.data.Post
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsRepositoryImpl @Inject constructor(private val api: PostsService): PostsRepository {

    override suspend fun getPosts(): List<Post> = withContext(CommonPool) {
        api.getPosts().await()
    }
}