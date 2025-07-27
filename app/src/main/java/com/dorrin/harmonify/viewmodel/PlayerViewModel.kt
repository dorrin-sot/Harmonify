package com.dorrin.harmonify.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.session.MediaController
import com.dorrin.harmonify.conversion.toMediaItem
import com.dorrin.harmonify.conversion.toTrack
import com.dorrin.harmonify.entities.TrackLike
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.provider.MediaControllerProvider
import com.dorrin.harmonify.repository.TrackLikeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class PlayerViewModel @Inject constructor(
  @ApplicationContext private val context: Context,
) : ViewModel() {
  @Inject
  lateinit var trackLikeRepository: TrackLikeRepository

  var mediaController: MediaController? = null

  private lateinit var mediaListener: CustomPlayerListener

  private val _playbackState = MutableLiveData<Int>()
  val isLoading: LiveData<Boolean> get() = _playbackState.map { it == Player.STATE_BUFFERING }

  private val _isPlaying = MutableLiveData<Boolean>()
  val isPlaying: LiveData<Boolean> get() = _isPlaying

  private val _isEnded = MutableLiveData<Boolean>()
  val isEnded: LiveData<Boolean> get() = _isEnded

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

  private val _currentTrackDurationMs = MutableLiveData<Long>()
  val currentTrackDurationMs: LiveData<Long> get() = _currentTrackDurationMs

  private val _seek = MutableLiveData<Float>()
  val seek: LiveData<Float> get() = _seek

  private val _currentTrackIsLiked = MutableLiveData<Boolean>(false)
  val currentTrackIsLiked: LiveData<Boolean> get() = _currentTrackIsLiked

  val canSkipPrevious: Boolean get() = mediaController?.hasPreviousMediaItem() == true
  val canSkipNext: Boolean get() = mediaController?.hasNextMediaItem() == true

  private var progressUpdateJob: Job? = null

  init {
    currentTrack.observeForever { updateCurrentTrackIsLiked(it) }

    isPlaying.observeForever { isPlaying ->
      if (isPlaying && progressUpdateJob?.isActive != true)
        startProgressUpdates()
    }

    isEnded.observeForever { isEnded -> if (isEnded) disposeController() }
  }

  override fun onCleared() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    disposeController()
    super.onCleared()
  }

  private suspend fun initializeController() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    if (mediaController == null) {
      val provider = MediaControllerProvider(context)

      mediaController = provider.awaitController()
      mediaListener = CustomPlayerListener()
      mediaController?.addListener(mediaListener)
    }
  }

  private fun disposeController() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    mediaController?.removeListener(mediaListener)
    mediaController?.release()
    mediaController = null
    progressUpdateJob?.cancel()
  }

  fun isInQueue(track: Track): Boolean {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    return playlist.value?.contains(track) == true
  }

  fun pause() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    mediaController?.pause()
  }

  fun play() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    viewModelScope.launch {
      initializeController()
      mediaController?.prepare()
      mediaController?.play()
    }
  }

  fun seekTo(percent: Float) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    val duration = currentTrackDurationMs.value ?: 0
    mediaController?.seekTo((percent * duration).toLong())
  }

  fun addToPlaylist(tracks: List<Track>) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    viewModelScope.launch {
      initializeController()
      mediaController?.addMediaItems(tracks.map { it.toMediaItem() })
      val isPlaying = isPlaying.value == true
      if (!isPlaying) play()
    }
  }

  fun removeFromPlaylist(tracks: List<Track>) {
    tracks
      .mapNotNull { playlist.value?.indexOf(it) }
      .mapNotNull { if (it == -1) null else it }
      .forEach { idx ->
        mediaController?.removeMediaItem(idx)
      }
  }

  fun skipPrevious() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    assert(canSkipPrevious)
    mediaController?.seekToPrevious()
  }

  fun skipNext() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    assert(canSkipNext)
    mediaController?.seekToNext()
  }

  fun setShuffleMode(shuffleMode: Boolean) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    mediaController?.shuffleModeEnabled = shuffleMode
  }

  fun setRepeatMode(repeatMode: Int) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    mediaController?.repeatMode = repeatMode
  }

  fun toggleLiked(track: Track) {
    viewModelScope.launch(Dispatchers.IO) {
      trackLikeRepository.toggleLiked(track.id, TrackLike(track = track))
      updateCurrentTrackIsLiked(track)
    }
  }

  private fun updateCurrentTrackIsLiked(track: Track) {
    viewModelScope.launch {
      _currentTrackIsLiked.value = viewModelScope.async(Dispatchers.IO) {
        return@async trackLikeRepository.isLiked(track.id)
      }.await()
    }
  }

  fun skipForward(seconds: Int) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    val total = currentTrackDurationMs.value?.toFloat() ?: 0f
    val currentPos = (seek.value ?: 0f) * total
    val newPosMSec = min(max(currentPos + seconds * 1000, 0f), total)
    seekTo(newPosMSec / total)
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
    override fun onEvents(player: Player, events: Player.Events) {
      object {}.javaClass.apply {
        println(
          "${enclosingClass?.name}::${enclosingMethod?.name} " +
              "${List(events.size()) { events.get(it) }}"
        )
      }
      if (events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED) &&
        player.playbackState == Player.STATE_READY
      ) {
        _currentTrackDurationMs.value = player.duration
      }
    }

    override fun onPlaybackStateChanged(@Player.State playbackState: Int) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _playbackState.value = playbackState

      _isEnded.value = playbackState == Player.STATE_ENDED ||
          (playbackState == Player.STATE_IDLE &&
              currentIndex.value == playlist.value?.size)
    }

    override fun onPlayWhenReadyChanged(
      playWhenReady: Boolean,
      @Player.PlayWhenReadyChangeReason reason: Int
    ) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      super.onPlayWhenReadyChanged(playWhenReady, reason)
      if (reason == Player.PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST) {
        _isPlaying.value = playWhenReady
      }
    }

    override fun onRepeatModeChanged(@Player.RepeatMode repeatMode: Int) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _repeatMode.value = repeatMode
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      _shuffleMode.value = shuffleModeEnabled
    }

    override fun onTimelineChanged(
      timeline: Timeline,
      @Player.TimelineChangeReason reason: Int
    ) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      updatePlaylist(timeline)
      updateCurrentIndex()
    }

    override fun onMediaItemTransition(
      mediaItem: MediaItem?,
      @Player.MediaItemTransitionReason reason: Int
    ) {
      object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
      updateCurrentIndex()
      updateSeekPosition()
    }

    override fun onPositionDiscontinuity(
      oldPosition: Player.PositionInfo,
      newPosition: Player.PositionInfo,
      @Player.DiscontinuityReason reason: Int
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
      val duration = currentTrackDurationMs.value ?: 0
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