package com.astimac.coroutines.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.astimac.coroutines.R
import com.astimac.coroutines.data.Post
import com.astimac.coroutines.ui.main.adapter.PostsAdapter
import com.astimac.coroutines.utils.Result
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    private val adapter = PostsAdapter()

    private val postsObserver = Observer<Result<List<Post>>> {
        when (it) {
            is Result.Loading -> {
                swipe_refresh_layout.isRefreshing = true
            }
            is Result.Success -> {
                swipe_refresh_layout.isRefreshing = false
                adapter.setData(it.data)
            }
            is Result.Error -> {
                swipe_refresh_layout.isRefreshing = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.liveData.observe(this, postsObserver)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        swipe_refresh_layout.setOnRefreshListener {
            viewModel.loadTasks()
        }

        viewModel.loadTasks()
    }
}
