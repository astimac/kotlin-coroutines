package com.astimac.coroutines.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.astimac.coroutines.data.Post
import com.astimac.coroutines.repository.PostsRepository
import com.astimac.coroutines.utils.CoroutineContextProvider
import com.astimac.coroutines.utils.Result
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: PostsRepository,
                                        private val contextPool: CoroutineContextProvider): ViewModel() {

    private val job = Job()
    val liveData = MutableLiveData<Result<List<Post>>>()

    init {
        liveData.value = Result.Loading()
    }

    fun loadTasks() = launch(contextPool.MAIN, parent = job) {
        try {
            val posts = if (liveData.value is Result.Success && !(liveData.value as Result.Success<List<Post>>).data.isEmpty()) {
                (liveData.value as Result.Success<List<Post>>).data
            } else {
                withContext(contextPool.BG) {
                    repository.getPosts()
                }
            }
            liveData.value = Result.Success(posts)
        } catch (e: Exception) {
            liveData.value = Result.Error(e.localizedMessage)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}