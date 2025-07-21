package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.apiservice.AlbumApiService
import com.dorrin.harmonify.model.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
  val albumApiService: AlbumApiService,
) : ViewModel() {
  private val _album = MutableLiveData<Album>()
  val album: LiveData<Album> get() = _album

  init {
    album.observeForever { updateTopTracks() }
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