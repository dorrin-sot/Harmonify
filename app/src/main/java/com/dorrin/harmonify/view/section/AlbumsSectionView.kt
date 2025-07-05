package com.dorrin.harmonify.view.section

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dorrin.harmonify.model.Album

@Composable
fun AlbumsSectionView(albums: List<Album>, modifier: Modifier) {
  SectionView(
    title = "Albums",
    items = albums,
    modifier = modifier
  ) { artist ->
    Card {
      Column {
        Text(artist.title)
      }
    }
  }
}