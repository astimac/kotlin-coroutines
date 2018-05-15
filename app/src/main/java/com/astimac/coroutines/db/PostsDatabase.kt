package com.astimac.coroutines.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.astimac.coroutines.data.Post

@Database(
        entities = [
            Post::class
        ],
        version = 1,
        exportSchema = false
)
abstract class PostsDatabase: RoomDatabase() {

    abstract fun postsDao(): PostsDao
}