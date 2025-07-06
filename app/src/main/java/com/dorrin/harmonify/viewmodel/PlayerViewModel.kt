package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.ViewModel
import com.dorrin.harmonify.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
) : ViewModel() {
  var isPlaying: Boolean = false
  var isPaused: Boolean = false

  fun start(track: Track) {
    TODO()
  }

  fun pause() {
    TODO()
  }

  fun resume() {
    TODO()
  }

  fun next() {
    TODO()
  }

  fun previous() {
    TODO()
  }

  fun stop() {
    TODO()
  }

  fun addToQueue(tracks: List<Track>) {
    TODO()
  }

  fun isCurrentInQueue(tracks: Track): Boolean {
    return false
    TODO()
  }
}