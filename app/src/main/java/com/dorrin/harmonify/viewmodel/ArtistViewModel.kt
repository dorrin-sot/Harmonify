package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.apiservice.ArtistApiService
import com.dorrin.harmonify.model.Artist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
  val artistApiService: ArtistApiService,
) : ViewModel() {
  private val _artist = MutableLiveData<Artist>()
  val artist: LiveData<Artist> get() = _artist

  private val _isLiked = MutableLiveData<Boolean>(false)
  val isLiked: LiveData<Boolean> get() = _isLiked

  init {
    artist.observeForever { updateTopTracks() }
    artist.observeForever { updateAlbums() }
  }

  private fun updateTopTracks() {
    artist.value?.run {
      if (topTracks != null) return@run

      viewModelScope.launch {
        _artist.value = copy(topTracks = artistApiService.getArtistTopTracks(id))
      }
    }
  }

  private fun updateAlbums() {
    artist.value?.run {
      if (albums != null) return@run

      viewModelScope.launch {
        _artist.value = copy(albums = artistApiService.getArtistAlbums(id))
      }
    }
  }

  fun setArtist(artist: Artist) {
    _artist.value = artist
  }
}