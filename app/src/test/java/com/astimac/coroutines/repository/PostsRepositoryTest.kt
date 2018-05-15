package com.astimac.coroutines.repository

import com.astimac.coroutines.api.PostsService
import com.astimac.coroutines.data.Post
import com.astimac.coroutines.db.PostsDao
import com.astimac.coroutines.utils.NetworkManager
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.runBlocking
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostsRepositoryTest {

    private lateinit var repository: PostsRepository

    @Mock
    private lateinit var api: PostsService

    @Mock
    private lateinit var dao: PostsDao

    @Mock
    private lateinit var networkManager: NetworkManager

    private val MANY_POSTS = listOf(
            Post(1, 1, "title1", "body1"),
            Post(2, 2, "title2", "body2"),
            Post(3, 3, "title3", "body3"))

    @Before
    fun setUp() {
        repository = PostsRepository(api, dao, networkManager)
    }

    @Test
    fun `calling getPosts should trigger PostsService-getPosts`() {
        given(networkManager.isConnected).willReturn(true)
        given(api.getPosts()).willReturn(CompletableDeferred(emptyList()))
        val posts = runBlocking { repository.getPosts() }

        verify(dao).deleteAll()
        verify(dao).insertAll(posts)
        verify(api).getPosts()
    }

    @Test
    fun `calling getPosts should return expected List`() {
        given(networkManager.isConnected).willReturn(true)
        given(api.getPosts()).willReturn(CompletableDeferred(MANY_POSTS))

        val posts = runBlocking { repository.getPosts() }

        verify(dao).deleteAll()
        verify(dao).insertAll(posts)
        assertThat(posts.size, Matchers.equalTo(3))
        assertThat(posts, Matchers.equalTo(MANY_POSTS))
    }

    @Test
    fun `calling getPosts should return empty list`() {
        given(networkManager.isConnected).willReturn(true)
        given(api.getPosts()).willReturn(CompletableDeferred(emptyList()))

        val posts = runBlocking { repository.getPosts() }

        verify(dao).deleteAll()
        verify(dao).insertAll(posts)
        assertThat(posts, Matchers.notNullValue())
        assertThat(posts.size, Matchers.equalTo(0))
    }
}