package com.dorrin.harmonify.view.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun AlbumsSectionView(albums: List<Album>, modifier: Modifier = Modifier) {
  val playerViewModel = hiltViewModel<PlayerViewModel>()

  CardSectionView(
    title = "Albums",
    items = albums,
    thumbnailGetter = { it.coverMedium },
    titleGetter = { it.title },
    trackGetter = { it.tracks?.data?.firstOrNull() },
    onEnqueue = { playerViewModel.addToPlaylist(it.tracks?.data ?: emptyList()) },
    modifier = modifier
  )
}