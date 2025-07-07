package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.dorrin.harmonify.conversion.toMediaItem
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.provider.MediaControllerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
  private val mediaControllerProvider: MediaControllerProvider
) : ViewModel() {
  val mediaController = mediaControllerProvider.getController()

  private lateinit var mediaListener: CustomPlayerListener
  private lateinit var playlistListener: (tracks: List<Track>) -> Unit

  private val _isPlaying = MutableLiveData<Boolean>()
  val isPlaying: LiveData<Boolean> get() = _isPlaying

  private val _isLoading = MutableLiveData<Boolean>()
  val isLoading: LiveData<Boolean> get() = _isLoading

  private val _repeatMode = MutableLiveData<Int>()
  val repeatMode: LiveData<Int> get() = _repeatMode

  private val _shuffleMode = MutableLiveData<Boolean>()
  val shuffleMode: LiveData<Boolean> get() = _shuffleMode

  private val _playlist = MutableLiveData<List<Track>>(emptyList())
  val playlist: LiveData<List<Track>> get() = _playlist

  private val _currentIndex = MutableLiveData<Int>()
  val currentIndex: LiveData<Int> get() = _currentIndex

  private val _currentTrack = MutableLiveData<Track>()
  val currentTrack: LiveData<Track> get() = _currentTrack

  private val _seek = MutableLiveData<Float>()
  val seek: LiveData<Float> get() = _seek

  init { initializeController() }

  private fun initializeController() =
    viewModelScope.launch { mediaControllerProvider.awaitController() }

  fun isInQueue(track: Track): Boolean {
    return playlist.value?.contains(track) == true
  }

  fun pause() {
    mediaController?.pause()
  }

  fun play() {
    mediaController?.prepare()
    mediaController?.play()
  }

  fun seekTo(percent: Float) {
    mediaController?.seekTo((percent * (currentTrack.value?.duration ?: 0) * 1000L).toLong())
  }

  fun addToPlaylist(tracks: List<Track>) {
    _playlist.postValue(listOf(*playlist.value!!.toTypedArray(), *tracks.toTypedArray()))
    mediaController?.addMediaItems(tracks.map { it.toMediaItem() })
  }

  inner class CustomPlayerListener : Player.Listener {
    override fun onEvents(player: Player, events: Player.Events) {
      println("CustomPlayerListener::onEvents $this $mediaController")
      super.onEvents(player, events)

      if (events.contains(Player.EVENT_IS_PLAYING_CHANGED))
        _isPlaying.postValue(player.isPlaying)

      if (events.contains(Player.EVENT_IS_LOADING_CHANGED))
        _isLoading.postValue(player.isLoading)

      if (events.contains(Player.EVENT_REPEAT_MODE_CHANGED))
        _repeatMode.postValue(player.repeatMode)

      if (events.contains(Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED))
        _shuffleMode.postValue(player.shuffleModeEnabled)

      if (events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION))
        _currentIndex.postValue(player.currentMediaItemIndex)

      if (events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED) ||
        events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)
      )
        _seek.postValue(player.currentPosition / player.duration.toFloat())
    }
  }
}