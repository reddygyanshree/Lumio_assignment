package com.example.tvassignment.data.repository

import com.example.tvassignment.data.model.Article

data class NewsResult(
    val articles: List<Article>,
    val fromCache: Boolean
)
