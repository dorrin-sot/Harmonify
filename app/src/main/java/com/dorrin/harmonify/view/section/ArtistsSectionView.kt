package com.dorrin.harmonify.view.section

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dorrin.harmonify.model.Artist

@Composable
internal fun ArtistsSectionView(artists: List<Artist>, modifier: Modifier = Modifier) {
  ProfileSectionView(
    title = "Artists",
    items = artists,
    thumbnailGetter = { it.pictureMedium },
    titleGetter = { it.name },
    modifier = modifier
  )
}