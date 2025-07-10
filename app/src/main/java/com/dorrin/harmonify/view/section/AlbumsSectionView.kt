package com.dorrin.harmonify.view.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dorrin.harmonify.model.Album

@Composable
internal fun AlbumsSectionView(albums: List<Album>, modifier: Modifier = Modifier) {
  CardSectionView(
    title = "Albums",
    items = albums,
    thumbnailGetter = { it.coverMedium },
    titleGetter = { it.title },
    trackGetter = { it.tracks?.data?.firstOrNull() },
    modifier = modifier
  )
}