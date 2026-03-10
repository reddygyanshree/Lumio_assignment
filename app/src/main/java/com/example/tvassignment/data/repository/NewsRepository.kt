package com.example.tvassignment.data.repository

import com.example.tvassignment.data.model.Article

interface NewsRepository {
    suspend fun getCachedNews(): List<Article>
    suspend fun fetchNews(apiKey: String): Result<NewsResult>
}