package com.example.tvassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvassignment.BuildConfig
import com.example.tvassignment.data.repository.NewsRepository
import com.example.tvassignment.data.repository.NewsRepositoryImpl
import com.example.tvassignment.data.repository.NewsResult
import com.example.tvassignment.state.UiState
import com.example.tvassignment.util.ConnectivityMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository:NewsRepository = NewsRepositoryImpl()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        observeConnectivity()
    }

    /** Reacts to TV/device internet state: when disconnected show cached data; when connected fetch/refresh. */
    private fun observeConnectivity() {
        ConnectivityMonitor.isConnected
            .onEach { connected ->
                if (!connected) {
                    viewModelScope.launch {
                        val cached = repository.getCachedNews()
                        if (cached.isNotEmpty()) {
                            _uiState.value = UiState.Success(articles = cached, fromCache = true)
                        } else {
                            _uiState.value = UiState.Error(
                                "No internet connection. Please check your network and try again."
                            )
                        }
                    }
                } else {
                    fetchNews()
                }
            }
            .launchIn(viewModelScope)
    }

    fun fetchNews() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            val result = repository.fetchNews(apiKey = BuildConfig.NEWS_API_KEY)

            _uiState.value = result.fold(
                onSuccess = { (articles, fromCache) ->
                    UiState.Success(articles = articles, fromCache = fromCache)
                },
                onFailure = { e ->
                    UiState.Error(e.message ?: "Unknown Error")
                }
            )
        }
    }

    fun refresh() {
        fetchNews()
    }
}