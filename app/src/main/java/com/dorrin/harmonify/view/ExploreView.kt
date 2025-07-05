package com.dorrin.harmonify.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.view.section.AlbumsSectionView
import com.dorrin.harmonify.view.section.ArtistsSectionView
import com.dorrin.harmonify.view.section.TracksSectionView
import com.dorrin.harmonify.viewmodel.ExploreViewModel

@Composable
fun ExploreView() {
  val viewModel = hiltViewModel<ExploreViewModel>()

  val chartArtists = viewModel.chartArtists.observeAsState()
  val chartAlbums = viewModel.chartAlbums.observeAsState()
  val chartTracks = viewModel.chartTracks.observeAsState()

  Column(
    modifier = Modifier
      .fillMaxSize()
  ) {
    ArtistsSectionView(
      chartArtists.value ?: emptyList(),
      modifier = Modifier.fillMaxHeight(.25f)
    )
    Divider()
    AlbumsSectionView(
      chartAlbums.value ?: emptyList(),
      modifier = Modifier.fillMaxHeight(.25f)
    )
    Divider()
    TracksSectionView(
      chartTracks.value ?: emptyList(),
      modifier = Modifier.fillMaxHeight(.5f)
    )
  }
}