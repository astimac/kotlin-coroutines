package com.astimac.coroutines.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
        val userId: Int,
        @PrimaryKey val id: Int,
        val title: String,
        val body: String
)