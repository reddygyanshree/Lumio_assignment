package com.example.tvassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tvassignment.data.model.Article
import com.example.tvassignment.ui.theme.HeaderBg

@Composable
fun NewsList(articles: List<Article>) {
    LazyColumn(
        modifier = Modifier
            .background(HeaderBg)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        items(articles) { article ->
            NewsItem(article)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}