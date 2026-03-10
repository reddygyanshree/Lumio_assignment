package com.example.tvassignment.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for offline caching of news articles.
 */
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String?,
    val description: String?,
    val urlToImage: String?
)
