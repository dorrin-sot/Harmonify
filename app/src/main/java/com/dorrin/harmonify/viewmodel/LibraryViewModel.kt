package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.dorrin.harmonify.dao.TrackDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
  trackDao: TrackDao,
) : ViewModel() {
  val likedTracks = trackDao.getAll().asLiveData()
    .map { it.map { it.track } }
}
