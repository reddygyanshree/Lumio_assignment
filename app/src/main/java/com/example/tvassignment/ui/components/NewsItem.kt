package com.example.tvassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.tvassignment.data.model.Article
import com.example.tvassignment.ui.theme.CardBackground
import com.example.tvassignment.ui.theme.CardBorder
import com.example.tvassignment.ui.theme.FocusBorder
import com.example.tvassignment.ui.theme.LoadingShimmer
import com.example.tvassignment.ui.theme.SummaryText
import com.example.tvassignment.ui.theme.TitleText

@Composable
fun NewsItem(article: Article) {
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var summaryOverflowsOneLine by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .border(
                width = if (isFocused) 3.dp else 1.dp,
                color = if (isFocused) FocusBorder else CardBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .focusable()
            .onFocusChanged { isFocused = it.isFocused }
            .clickable { expanded = !expanded }
            .padding(20.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Article image: fixed size for efficiency, Coil handles memory + disk cache
        val imageModifier = Modifier
            .size(width = 200.dp, height = 120.dp)
            .clip(RoundedCornerShape(8.dp))

        if (!article.urlToImage.isNullOrBlank()) {
            SubcomposeAsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = imageModifier.background(LoadingShimmer),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = SummaryText,
                            strokeWidth = 2.dp
                        )
                    }
                },
                error = {
                    PlaceholderBox(modifier = imageModifier)
                }
            )
        } else {
            PlaceholderBox(modifier = imageModifier)
        }

        Spacer(modifier = Modifier.width(24.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = article.title ?: "No title",
                color = TitleText,
                fontSize = 24.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            // Summary: one line initially; full text when expanded (tap to toggle). Show expand hint only if summary is longer than one line.
            Text(
                text = article.description ?: "",
                color = SummaryText,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                maxLines = if (expanded) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { if (!expanded) summaryOverflowsOneLine = it.hasVisualOverflow }
            )
            if (!article.description.isNullOrBlank() && (expanded || summaryOverflowsOneLine)) {
                Text(
                    text = if (expanded) "Tap to collapse" else "Tap to expand full summary",
                    color = SummaryText,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun PlaceholderBox(modifier: Modifier) {
    androidx.compose.foundation.layout.Box(
        modifier = modifier.background(LoadingShimmer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No image",
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}
