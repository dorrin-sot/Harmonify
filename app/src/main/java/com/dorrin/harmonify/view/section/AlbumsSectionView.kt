package com.dorrin.harmonify.view.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
internal fun AlbumsSectionView(
  albums: List<Album>,
  modifier: Modifier = Modifier,
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
) {
  CardSectionView(
    title = "Albums",
    items = albums,
    thumbnailGetter = { it.coverMedium },
    titleGetter = { it.title },
    artistGetter = { it.artist?.name ?: "" },
    trackGetter = { it.tracks?.data?.firstOrNull() },
    modifier = modifier,
    playerViewModel = playerViewModel,
  )
}