package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.session.MediaController
import com.dorrin.harmonify.conversion.toMediaItem
import com.dorrin.harmonify.conversion.toTrack
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.provider.MediaControllerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class PlayerViewModel @Inject constructor(
  val mediaControllerProvider: MediaControllerProvider
) : ViewModel() {
  var mediaController: MediaController? = null

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

  private val _currentTrack = MutableLiveData<Track>()
  val currentTrack: LiveData<Track?> get() = _currentTrack

  private val _seek = MutableLiveData<Float>()
  val seek: LiveData<Float> get() = _seek

  val canSkipPrevious: Boolean get() = mediaController?.hasPreviousMediaItem() == true
  val canSkipNext: Boolean get() = mediaController?.hasNextMediaItem() == true

  private var progressUpdateJob: Job? = null

  init {
    initializeController()
    isPlaying.observeForever { isPlaying ->
      if (isPlaying && progressUpdateJob?.isActive != true)
        startProgressUpdates()
    }

    currentIndex.observeForever { }
  }

  override fun onCleared() {
    mediaController?.removeListener(mediaListener)
    mediaController?.release()
    progressUpdateJob?.cancel()
    super.onCleared()
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
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name} $mediaController ${playlist.value} ${currentIndex.value} ${currentTrack.value}") }
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

  private fun startProgressUpdates() {
    progressUpdateJob?.cancel()
    progressUpdateJob = viewModelScope.launch {
      while (true) {
        delay(1000) // Update every second
        if (isPlaying.value == true) {
          mediaListener.updateSeekPosition()
        }
      }
    }
  }

  inner class CustomPlayerListener : Player.Listener {
    override fun onPlaybackStateChanged(playbackState: Int) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _isLoading.value = (playbackState == Player.STATE_BUFFERING)
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _isLoading.value = isLoading
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _isPlaying.value = isPlaying
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _repeatMode.value = repeatMode
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _shuffleMode.value = shuffleModeEnabled
    }

    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      updatePlaylist(timeline)
      updateCurrentIndex()
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      updateCurrentIndex()
      updateSeekPosition()
    }

    override fun onPositionDiscontinuity(
      oldPosition: Player.PositionInfo,
      newPosition: Player.PositionInfo,
      reason: Int
    ) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      updateSeekPosition()
    }

    private fun updateCurrentIndex() {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _currentIndex.value = mediaController?.currentMediaItemIndex
      playlist.value
        ?.let { playlist ->
          playlist.ifEmpty { null } ?: return@let
          val index = currentIndex.value
          index ?: return@let
          val track = playlist.getOrNull(index)
          track ?: return@let
          _currentTrack.value = track
        }

    }

    internal fun updateSeekPosition() {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      val duration = mediaController?.duration ?: 0
      if (duration > 0) {
        _seek.value = mediaController?.currentPosition?.toFloat()?.div(duration)
      }
    }

    private fun updatePlaylist(timeline: Timeline) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      List(timeline.windowCount) {
        timeline.getWindow(it, Timeline.Window())
          .mediaItem
          .toTrack()
      }.filterNotNull()
        .ifEmpty { null }
        ?.let { _playlist.value = it }
    }
  }
}