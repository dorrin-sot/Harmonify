package com.dorrin.harmonify.view.downloads

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.viewmodel.BottomSheetType.DOWNLOADS_BOTTOM_SHEET
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel
import com.dorrin.harmonify.viewmodel.DownloadsViewModel

@Composable
fun DownloadsIconButton(
  bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
  downloadsViewModel: DownloadsViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  val totalTracksCount by downloadsViewModel.totalTracksCount.observeAsState(0)
  val downloadingTracksCount by downloadsViewModel.downloadingTracksCount.observeAsState(0)
  val totalProgress by downloadsViewModel.totalProgress.observeAsState(0f)

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
      IconButton(
        onClick = { bottomSheetViewModel.showBottomSheet(DOWNLOADS_BOTTOM_SHEET) }
      ) {
        Icon(Icons.Default.ArrowDownward, "Download For Offline")
      }
    }
  }
}