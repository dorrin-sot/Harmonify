package com.dorrin.harmonify.view.section

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dorrin.harmonify.model.Artist

@Composable
fun ArtistsSectionView(artists: List<Artist>, modifier: Modifier) {
  SectionView(
    title = "Artists",
    items = artists,
    modifier = modifier
  ) { artist ->
    Card {
      Column {
        Text(artist.name)
      }
    }
  }
}