package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.ViewModel
import com.dorrin.harmonify.dao.AlbumDao
import com.dorrin.harmonify.dao.TrackDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
  private val albumDao: AlbumDao,
  private val trackDao: TrackDao,
) : ViewModel() {
}