package com.astimac.coroutines.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.astimac.coroutines.data.Post
import android.arch.persistence.room.Delete

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<Post>)

    @Delete
    fun delete(post: Post)

    @Query("DELETE FROM posts")
    fun deleteAll()

    @Query("SELECT * FROM posts")
    fun selectAll(): List<Post>

    @Query("SELECT COUNT(id) FROM posts")
    fun count(): Int
}