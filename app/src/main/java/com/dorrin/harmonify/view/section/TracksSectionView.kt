package com.dorrin.harmonify.view.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun TracksSectionView(tracks: List<Track>, modifier: Modifier = Modifier) {
  val playerViewModel = hiltViewModel<PlayerViewModel>()

  GridSectionView(
    title = "Tracks",
    items = tracks,
    titleGetter = { it.title },
    thumbnailGetter = { it.album.coverMedium },
    trackGetter = { it },
    onEnqueue = { playerViewModel.addToPlaylist(listOf(it)) },
    modifier = modifier
  )
}