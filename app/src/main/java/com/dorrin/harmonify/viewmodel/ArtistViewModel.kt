package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.apiservice.ArtistApiService
import com.dorrin.harmonify.entities.ArtistLike
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.repository.ArtistLikeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
  val artistApiService: ArtistApiService,
) : ViewModel() {
  @Inject
  lateinit var artistLikeRepository: ArtistLikeRepository

  private val _artist = MutableLiveData<Artist>()
  val artist: LiveData<Artist> get() = _artist

  private val _isLiked = MutableLiveData<Boolean>(false)
  val isLiked: LiveData<Boolean> get() = _isLiked

  init {
    artist.observeForever { updateIsLiked(it) }
    artist.observeForever { updateTopTracks() }
    artist.observeForever { updateAlbums() }
  }

  private fun updateIsLiked(artist: Artist) {
    viewModelScope.launch {
      _isLiked.value = viewModelScope.async(Dispatchers.IO) {
        return@async artistLikeRepository.isLiked(artist.id)
      }.await()
    }
  }

  fun toggleLiked() {
    val artist = artist.value
    artist ?: return

    viewModelScope.launch(Dispatchers.IO) {
      artistLikeRepository.toggleLiked(artist.id, ArtistLike(artist = artist))
      updateIsLiked(artist)
    }
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