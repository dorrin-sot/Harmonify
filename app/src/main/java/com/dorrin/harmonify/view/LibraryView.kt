package com.dorrin.harmonify.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.view.section.TracksSectionView
import com.dorrin.harmonify.viewmodel.LibraryViewModel

@Composable
fun LibraryView() {
  val viewModel = hiltViewModel<LibraryViewModel>()

  val tracks by viewModel.likedTracks.observeAsState(emptyList())

  TracksSectionView(tracks, maxItemsInEachRow = 2)
}