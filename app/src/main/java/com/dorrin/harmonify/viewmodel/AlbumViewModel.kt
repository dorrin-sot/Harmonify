package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.apiservice.AlbumApiService
import com.dorrin.harmonify.entities.AlbumLike
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.repository.AlbumLikeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
  val albumApiService: AlbumApiService,
) : ViewModel() {
  @Inject
  lateinit var albumLikeRepository: AlbumLikeRepository

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
      _isLiked.value = viewModelScope.async(Dispatchers.IO) {
        return@async albumLikeRepository.isLiked(album.id)
      }.await()
    }
  }

  fun toggleLiked() {
    val album = album.value
    album ?: return

    viewModelScope.launch(Dispatchers.IO) {
      albumLikeRepository.toggleLiked(album.id, AlbumLike(album = album))
      updateIsLiked(album)
    }
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