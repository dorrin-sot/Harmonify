package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.dorrin.harmonify.dao.AlbumDao
import com.dorrin.harmonify.dao.ArtistDao
import com.dorrin.harmonify.dao.TrackDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
  private val trackDao: TrackDao,
  private val albumDao: AlbumDao,
  private val artistDao: ArtistDao,
) : ViewModel() {
  val likedTracks
    get() = trackDao.getAll().asLiveData()
      .map { it.map { it.track } }

  val likedAlbums
    get() = albumDao.getAll().asLiveData()
      .map { it.map { it.album } }

  val likedArtists
    get() = artistDao.getAll().asLiveData()
      .map { it.map { it.artist } }
}
