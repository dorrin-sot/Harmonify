package com.dorrin.harmonify.view.section

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dorrin.harmonify.model.Track

@Composable
fun TracksSectionView(tracks: List<Track>, modifier: Modifier) {
  SectionView(
    title = "Tracks",
    items = tracks,
    modifier = modifier
  ) { track ->
    Card {
      Column {
        Text(track.title)
      }
    }
  }
}