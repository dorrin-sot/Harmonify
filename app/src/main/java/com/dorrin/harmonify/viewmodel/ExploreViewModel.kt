package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.model.Chart
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.apiservice.ChartApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
  val chartApiService: ChartApiService
) : ViewModel() {
  private val _chart = MutableLiveData<Chart>()

  val chartTracks: LiveData<List<Track>> get() = _chart.map { it.tracks.data }
  val chartAlbums: LiveData<List<Album>> get() = _chart.map { it.albums.data }
  val chartArtists: LiveData<List<Artist>> get() = _chart.map { it.artists.data }

  init { getChart() }

  fun getChart() = viewModelScope.launch {
    _chart.value = (chartApiService.getChart())
  }
}