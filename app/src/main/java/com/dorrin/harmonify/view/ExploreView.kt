package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
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

  val searchOn = searchViewModel.searchOn.observeAsState()
  val searchQuery = searchViewModel.query.observeAsState()

  val search = searchViewModel.result.observeAsState()
  val chart = exploreViewModel.chart.observeAsState()

  val isSearch =
    remember { derivedStateOf { searchOn.value == true && !searchQuery.value.isNullOrEmpty() } }

  val results by remember { derivedStateOf { if (isSearch.value) search else chart } }
  LaunchedEffect(Unit) {
    exploreViewModel.getChart()
  }

  LazyColumn {
    item { AlbumsSectionView(albums = results.value?.albums?.data ?: emptyList()) }
    item { HorizontalDivider() }
    item { ArtistsSectionView(artists = results.value?.artists?.data ?: emptyList()) }
    item { HorizontalDivider() }
    item { TracksSectionView(tracks = results.value?.tracks?.data ?: emptyList()) }
  }
}