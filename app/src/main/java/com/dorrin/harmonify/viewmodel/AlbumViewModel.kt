package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.apiservice.AlbumApiService
import com.dorrin.harmonify.entities.TrackLike
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.repository.TrackLikeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
  val albumApiService: AlbumApiService,
) : ViewModel() {
  @Inject
  lateinit var trackLikeRepository: TrackLikeRepository

  private val _album = MutableLiveData<Album>()
  val album: LiveData<Album> get() = _album

  private val _isLiked = MutableLiveData<Boolean>(false)
  val isLiked: LiveData<Boolean> get() = _isLiked

  init {
    album.observeForever { updateIsLiked(it) }
    album.observeForever { updateTopTracks() }
  }

  private fun updateIsLiked(album: Album) {
    viewModelScope.launch {
      _isLiked.value = allSongsInAlbumAreLiked(album)
    }
  }

  private suspend fun allSongsInAlbumAreLiked(album: Album): Boolean =
    album.tracks?.data?.map { track ->
      viewModelScope.async(Dispatchers.IO) {
        trackLikeRepository.isLiked(track.id)
      }
    }?.awaitAll()?.reduce { b1, b2 -> b1 && b2 } ?: false

  fun toggleLiked() {
    val album = album.value
    album ?: return

    viewModelScope.launch(Dispatchers.IO) {
      toggleLikedForAllSongsInAlbum(album)
      updateIsLiked(album)
    }
  }

  private suspend fun toggleLikedForAllSongsInAlbum(album: Album) {
    val liked = allSongsInAlbumAreLiked(album)
    album.tracks?.data?.map { track ->
      viewModelScope.async(Dispatchers.IO) {
        trackLikeRepository.setLiked(TrackLike(track = track), !liked)
      }
    }?.awaitAll()
  }

  private fun updateTopTracks() {
    album.value?.run {
      if (tracks != null) return

      viewModelScope.launch {
        albumApiService.getAlbumTracks(id)
          .let { tracks ->
            tracks.copy(
              tracks.data.map { it.copy(album = this@run) }
            )
          }.let { _album.value = copy(tracks = it) }
      }
    }
  }

  fun setAlbum(album: Album) {
    _album.value = album
  }
}