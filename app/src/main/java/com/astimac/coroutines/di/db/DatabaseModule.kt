package com.astimac.coroutines.di.db

import android.arch.persistence.room.Room
import android.content.Context
import com.astimac.coroutines.db.PostsDao
import com.astimac.coroutines.db.PostsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): PostsDatabase {
        return Room
                .databaseBuilder(context, PostsDatabase::class.java, "posts.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun providePostsDao(db: PostsDatabase): PostsDao {
        return db.postsDao()
    }
}