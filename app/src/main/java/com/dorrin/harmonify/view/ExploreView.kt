package com.dorrin.harmonify.view

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
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

  LazyColumn(
    modifier = Modifier.fillMaxSize()
  ) {
    item { AlbumsSectionView(chartAlbums.value ?: emptyList()) }
    item { ArtistsSectionView(chartArtists.value ?: emptyList()) }
    item {
      TracksSectionView(
        chartTracks.value ?: emptyList(),
        modifier = Modifier.fillParentMaxHeight()
      )
    }
  }
}