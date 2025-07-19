package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.dorrin.harmonify.model.emptyResult
import com.dorrin.harmonify.view.section.AlbumsSectionView
import com.dorrin.harmonify.view.section.ArtistsSectionView
import com.dorrin.harmonify.view.section.TracksSectionView
import com.dorrin.harmonify.viewmodel.ExploreViewModel
import com.dorrin.harmonify.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreView() {
  val exploreViewModel = hiltViewModel<ExploreViewModel>()
  val searchViewModel = hiltViewModel<SearchViewModel>(LocalActivity.current as ComponentActivity)

  val searchOn by searchViewModel.searchOn.observeAsState(false)
  val searchQuery by searchViewModel.query.observeAsState()

  val search by searchViewModel.result.observeAsState(emptyResult())
  val chart by exploreViewModel.chart.observeAsState(emptyResult())

  val isSearch by remember { derivedStateOf { searchOn && !searchQuery.isNullOrEmpty() } }

  val results by remember { derivedStateOf { if (isSearch) search else chart } }

  val lifecycleOwner = LocalLifecycleOwner.current
  DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
      if (event == Lifecycle.Event.ON_START) exploreViewModel.getChart()
    }

    lifecycleOwner.lifecycle.addObserver(observer)

    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
  }

  LazyColumn {
    item { AlbumsSectionView(albums = results.albums.data) }
    item { HorizontalDivider() }
    item { ArtistsSectionView(artists = results.artists.data) }
    item { HorizontalDivider() }
    item { TracksSectionView(tracks = results.tracks.data) }
  }
}