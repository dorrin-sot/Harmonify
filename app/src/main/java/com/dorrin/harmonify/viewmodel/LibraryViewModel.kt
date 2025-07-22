package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.dao.AlbumDao
import com.dorrin.harmonify.dao.ArtistDao
import com.dorrin.harmonify.dao.TrackDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
  private val trackDao: TrackDao,
  private val albumDao: AlbumDao,
  private val artistDao: ArtistDao,
) : ViewModel() {
  val likedTracks get() = trackDao.getAllLiked()
  val likedAlbums get() = albumDao.getAllLiked()
  val likedArtists get() = artistDao.getAllLiked()
}