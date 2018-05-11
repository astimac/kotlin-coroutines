package com.astimac.coroutines.api

import com.astimac.coroutines.data.Post
import com.astimac.coroutines.utils.Result
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface PostsService {

    @GET("posts")
    fun getPosts(): Deferred<List<Post>>
}