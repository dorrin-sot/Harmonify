package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.dorrin.harmonify.conversion.toMediaItem
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.provider.MediaControllerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class PlayerViewModel @Inject constructor(
  private val mediaControllerProvider: MediaControllerProvider
) : ViewModel() {
  private var mediaController: MediaController? = null

  private lateinit var mediaListener: CustomPlayerListener

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

  val currentTrack: LiveData<Track?> get() = currentIndex.map { playlist.value?.get(it) }

  private val _seek = MutableLiveData<Float>()
  val seek: LiveData<Float> get() = _seek

  val canSkipPrevious: Boolean get() = mediaController?.hasPreviousMediaItem() == true
  val canSkipNext: Boolean get() = mediaController?.hasNextMediaItem() == true

  init {
    initializeController()
  }

  private fun initializeController() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }

    viewModelScope.launch {
      mediaController = mediaControllerProvider.awaitController()
      mediaListener = CustomPlayerListener()
      mediaController?.addListener(mediaListener)
    }
  }

  fun isInQueue(track: Track): Boolean {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    return playlist.value?.contains(track) == true
  }

  fun pause() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    mediaController?.pause()
  }

  fun play() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    mediaController?.prepare()
    mediaController?.play()
  }

  fun seekTo(percent: Float) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    mediaController?.seekTo((percent * (currentTrack.value?.duration ?: 0) * 1000L).toLong())
  }

  fun addToPlaylist(tracks: List<Track>) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    _playlist.value =
      listOf(
        *playlist.value?.toTypedArray() ?: emptyArray(),
        *tracks.toTypedArray()
      )
    mediaController?.addMediaItems(tracks.map { it.toMediaItem() })
  }

  fun skipPrevious() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    assert(canSkipPrevious)
    mediaController?.seekToPrevious()
  }

  fun skipNext() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    assert(canSkipNext)
    mediaController?.seekToNext()
  }

  fun setShuffleMode(shuffleMode: Boolean) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    mediaController?.shuffleModeEnabled = shuffleMode
  }

  fun setRepeatMode(repeatMode: Int) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    mediaController?.repeatMode = repeatMode
  }

  fun skipForward(seconds: Int) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    val total = currentTrack.value!!.duration.toFloat()
    val currentPos = (seek.value ?: 0f) * total
    val newPosSec = max(min(currentPos + seconds, 0f), total)
    val newPosMSec = newPosSec * 1000L
    seekTo(newPosMSec)
  }

  fun skipBackward(seconds: Int) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController") }
    val total = currentTrack.value!!.duration.toFloat()
    val currentPos = (seek.value ?: 0f) * total
    val newPosSec = max(min(currentPos - seconds, 0f), total)
    val newPosMSec = newPosSec * 1000L
    seekTo(newPosMSec)
  }

  inner class CustomPlayerListener : Player.Listener {
    override fun onEvents(player: Player, events: Player.Events) {
      object {}.javaClass.apply {
        println(
          "${enclosingClass?.name}::${enclosingMethod?.name} " +
              "${List(events.size()) { events.get(it) }}"
        )
      }
      super.onEvents(player, events)

      if (events.contains(Player.EVENT_IS_PLAYING_CHANGED))
        _isPlaying.postValue(player.isPlaying)

      if (events.contains(Player.EVENT_IS_LOADING_CHANGED))
        _isLoading.postValue(player.isLoading)

      if (events.contains(Player.EVENT_REPEAT_MODE_CHANGED))
        _repeatMode.postValue(player.repeatMode)

      if (events.contains(Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED))
        _shuffleMode.postValue(player.shuffleModeEnabled)

      if (
        events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION) ||
        events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED) ||
        events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)
      ) {
        _currentIndex.postValue(player.currentMediaItemIndex)
        _seek.postValue(player.currentPosition / player.duration.toFloat())
      }
    }
  }
}