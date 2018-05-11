package com.astimac.coroutines.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.astimac.coroutines.data.Post
import com.astimac.coroutines.repository.PostsRepositoryImpl
import com.astimac.coroutines.utils.Result
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: PostsRepositoryImpl): ViewModel() {

    private val job = Job()
    val liveData = MutableLiveData<Result<List<Post>>>()

    init {
        loadTasks()
    }

    fun loadTasks() = launch(UI, parent = job) {
        liveData.value = Result.Loading()
        try {
            val posts = repository.getPosts()
            liveData.value = Result.Success(posts)
        } catch (e: Exception) {
            liveData.value = Result.Error(e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}