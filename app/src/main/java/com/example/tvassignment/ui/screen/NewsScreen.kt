package com.example.tvassignment.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tvassignment.state.UiState
import com.example.tvassignment.ui.components.NewsHeader
import com.example.tvassignment.ui.components.NewsList
import com.example.tvassignment.ui.theme.ErrorText
import com.example.tvassignment.ui.theme.HeaderBg
import com.example.tvassignment.ui.theme.HeaderSubtitle
import com.example.tvassignment.viewmodel.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HeaderBg)
            .onPreviewKeyEvent { event ->
                if (event.key == Key.DirectionDown &&
                    event.type == KeyEventType.KeyDown &&
                    event.nativeKeyEvent.repeatCount > 5
                ) {
                    viewModel.refresh()
                    true
                } else false
            }
    ) {
        NewsHeader()

        Box(modifier = Modifier.fillMaxSize()) {
            when (state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UiState.Success -> {
                    val success = state as UiState.Success
                    Column(modifier = Modifier.fillMaxSize()) {
                        if (success.fromCache) {
                            Text(
                                text = "No internet. Showing cached news.",
                                color = HeaderSubtitle,
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                        NewsList(articles = success.articles)
                    }
                }

                is UiState.Error -> {
                    Text(
                        text = (state as UiState.Error).message,
                        color = ErrorText,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}