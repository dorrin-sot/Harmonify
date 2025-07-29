package com.dorrin.harmonify.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.work.WorkInfo.State.BLOCKED
import androidx.work.WorkInfo.State.ENQUEUED
import androidx.work.WorkInfo.State.FAILED
import androidx.work.WorkInfo.State.RUNNING
import androidx.work.WorkInfo.State.SUCCEEDED
import com.dorrin.harmonify.entities.TrackLike
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.repository.TrackLikeRepository
import com.dorrin.harmonify.worker.track.TrackDownloadWorkerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
  trackLikeRepository: TrackLikeRepository,
  private val downloadWorker: TrackDownloadWorkerManager,
) : ViewModel() {
  private val _tracks: Flow<List<TrackLike>> = trackLikeRepository.allItems
    .onEach { Log.d("harmonify", "$it") }
    .onEmpty { emit(emptyList()) }

  val tracks: LiveData<List<TrackLike>> get() = _tracks.asLiveData()

  val pendingTracks: LiveData<List<TrackLike>>
    get() = tracks.map { it.filter { it.state == ENQUEUED || it.state == BLOCKED } }
  val downloadingTracks: LiveData<List<TrackLike>>
    get() = tracks.map { it.filter { it.state == RUNNING } }
  val downloadedTracks: LiveData<List<TrackLike>>
    get() = tracks.map { it.filter { it.state == SUCCEEDED } }
  val failedTracks: LiveData<List<TrackLike>>
    get() = tracks.map { it.filter { it.state == FAILED } }

  val pendingTracksCount: LiveData<Int> get() = pendingTracks.map { it.size }
  val downloadingTracksCount: LiveData<Int> get() = downloadingTracks.map { it.size }
  val downloadedTracksCount: LiveData<Int> get() = downloadedTracks.map { it.size }
  val failedTracksCount: LiveData<Int> get() = failedTracks.map { it.size }
  val totalTracksCount: LiveData<Int> get() = tracks.map { it.size }

  val totalProgress: LiveData<Float>
    get() = tracks.map { it.map { it: TrackLike -> it.progress ?: 0f }.average().toFloat() }

  fun start(track: Track) = downloadWorker.schedule(track)

  fun stop(track: Track) = downloadWorker.cleanup(track)
}