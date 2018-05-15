package com.astimac.coroutines.ui.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.astimac.coroutines.TestCoroutineContextProvider
import com.astimac.coroutines.data.Post
import com.astimac.coroutines.repository.PostsRepository
import com.astimac.coroutines.utils.Result
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import kotlinx.coroutines.experimental.runBlocking
import org.hamcrest.MatcherAssert.*
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel

    @Mock
    private lateinit var repository: PostsRepository

    private val MANY_POSTS = listOf(
            Post(1, 1, "title1", "body1"),
            Post(2, 2, "title2", "body2"),
            Post(3, 3, "title3", "body3"))

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(repository, TestCoroutineContextProvider())
    }

    @Test
    fun `initialised view model should have Loading state`() {
        assertThat(mainViewModel.liveData.value, instanceOf(Result.Loading::class.java))
    }

    @Test
    fun `throwing exception should return Result Error`() {
        given { runBlocking { repository.getPosts() }}.willThrow(RuntimeException("error"))

        val observer = mock<Observer<Result<List<Post>>>>()
        mainViewModel.liveData.observeForever(observer)

        runBlocking { mainViewModel.loadTasks() }

        assertThat(mainViewModel.liveData.value, instanceOf(Result.Error::class.java))

        val message = (mainViewModel.liveData.value as Result.Error).message
        assertThat(message, equalTo("error"))
    }

    @Test
    fun `loading posts should return Result Success`() {
        given { runBlocking { repository.getPosts() }}.willReturn(MANY_POSTS)

        runBlocking { mainViewModel.loadTasks() }

        mainViewModel.liveData.observeForever {
            assertThat(it, notNullValue())
            assertThat(mainViewModel.liveData.value, instanceOf(Result.Success::class.java))
            val data = (it as Result.Success).data
            assertThat(data.size, equalTo(MANY_POSTS.size))
            assertThat(data[2].title, equalTo("title3"))
        }
    }

    @Test
    fun `loading empty post list should return Result Success`() {
        given { runBlocking { repository.getPosts() }}.willReturn(emptyList())

        runBlocking { mainViewModel.loadTasks() }

        mainViewModel.liveData.observeForever {
            assertThat(it, notNullValue())
            assertThat(mainViewModel.liveData.value, instanceOf(Result.Success::class.java))
            val data = (it as Result.Success).data
            assertThat(data.size, equalTo(0))
        }
    }
}