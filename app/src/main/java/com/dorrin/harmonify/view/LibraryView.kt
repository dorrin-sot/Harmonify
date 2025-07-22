package com.dorrin.harmonify.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.view.section.AlbumsSectionView
import com.dorrin.harmonify.view.section.ArtistsSectionView
import com.dorrin.harmonify.view.section.TracksSectionView
import com.dorrin.harmonify.viewmodel.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryView() {
  val viewModel = hiltViewModel<LibraryViewModel>()

  val albums by viewModel.likedAlbums.observeAsState(emptyList())
  val artists by viewModel.likedArtists.observeAsState(emptyList())
  val tracks by viewModel.likedTracks.observeAsState(emptyList())

  LazyColumn {
    item { AlbumsSectionView(albums) }
    item { HorizontalDivider() }
    item { ArtistsSectionView(artists) }
    item { HorizontalDivider() }
    item { TracksSectionView(tracks, maxItemsInEachRow = 2) }
  }
}