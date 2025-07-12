package com.dorrin.harmonify.view.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun TracksSectionView(
  modifier: Modifier = Modifier,
  tracks: List<Track>,
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  GridSectionView(
    title = "Tracks",
    items = tracks,
    titleGetter = { it.title },
    thumbnailGetter = { it.album.coverMedium },
    trackGetter = { it },
    modifier = modifier,
    playerViewModel = playerViewModel,
  )
}