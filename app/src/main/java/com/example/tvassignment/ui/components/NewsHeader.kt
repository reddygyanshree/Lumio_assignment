package com.example.tvassignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tvassignment.ui.theme.HeaderBg
import com.example.tvassignment.ui.theme.HeaderSubtitle
import com.example.tvassignment.ui.theme.HeaderTitle

@Composable
fun NewsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderBg)
            .padding(horizontal = 32.dp, vertical = 24.dp)
    ) {
        androidx.compose.material3.Text(
            text = "News Headlines",
            color = HeaderTitle,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Text(
            text = "Long-press DPAD Down to refresh • Cached for offline reading",
            color = HeaderSubtitle,
            fontSize = 18.sp
        )
    }
}
