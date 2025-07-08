package com.dorrin.harmonify.view.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dorrin.harmonify.model.Track

@Composable
fun TracksSectionView(tracks: List<Track>, modifier: Modifier = Modifier) {
  GridSectionView(
    title = "Tracks",
    items = tracks,
    titleGetter = { it.title },
    thumbnailGetter = { it.album.coverMedium },
    trackGetter = { it },
    modifier = modifier
  )
}