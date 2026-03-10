package com.example.tvassignment.state

import com.example.tvassignment.data.model.Article

sealed class UiState {
    object Loading : UiState()
    data class Success(val articles: List<Article>, val fromCache: Boolean = false) : UiState()
    data class Error(val message: String) : UiState()
}