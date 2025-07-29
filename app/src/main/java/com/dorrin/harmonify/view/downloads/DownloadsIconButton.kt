package com.dorrin.harmonify.view.downloads

import androidx.annotation.FloatRange
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DownloadsIconButton(
  totalTracksCount: Int,
  downloadingTracksCount: Int,
  @FloatRange(0.0, 1.0) totalProgress: Float,
  onClick: () -> Unit = {},
) {
  Box(contentAlignment = Alignment.Center) {
    if (totalTracksCount > 0)
      CircularProgressIndicator(progress = { totalProgress })

    BadgedBox(
      badge = {
        if (totalTracksCount > 0)
          Badge {
            Text(
              "$downloadingTracksCount",
              fontWeight = FontWeight.Bold,
            )
          }
      },
    ) {
      IconButton(onClick = onClick) {
        Icon(Icons.Default.ArrowDownward, "Download For Offline")
      }
    }
  }
}