package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.work.WorkInfo
import androidx.work.WorkInfo.State.BLOCKED
import androidx.work.WorkInfo.State.ENQUEUED
import androidx.work.WorkInfo.State.FAILED
import androidx.work.WorkInfo.State.RUNNING
import androidx.work.WorkInfo.State.SUCCEEDED
import androidx.work.WorkManager
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.repository.AlbumLikeRepository
import com.dorrin.harmonify.repository.TrackLikeRepository
import com.dorrin.harmonify.worker.DownloadWorkerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
  private val albumLikeRepository: AlbumLikeRepository,
  private val trackLikeRepository: TrackLikeRepository,
) : ViewModel() {
  private val _albumTracks: Flow<List<Track>>
    get() = albumLikeRepository.getAll()
      .map { it.flatMap { it.album.tracks?.data ?: emptyList() } }

  private val _singleTracks: Flow<List<Track>>
    get() = trackLikeRepository.getAll()
      .map { it.map { it.track } }

  private val tracks: LiveData<Set<Track>>
    get() = _albumTracks.combine(_singleTracks) { albumTracks, singleTracks ->
      setOf(*albumTracks.toTypedArray(), *singleTracks.toTypedArray())
    }.asLiveData()

  private val _tracksWithStatus = MutableLiveData<Map<Track, WorkInfo>>()
  val tracksWithStatus: LiveData<Map<Track, WorkInfo>> get() = _tracksWithStatus

  val pendingTracks: LiveData<Set<Track>>
    get() = tracksWithStatus
      .map { it.filter { it.value.state == ENQUEUED || it.value.state == BLOCKED }.keys }
  val downloadingTracks: LiveData<Set<Track>>
    get() = tracksWithStatus.map { it.filter { it.value.state == RUNNING }.keys }
  val downloadedTracks: LiveData<Set<Track>>
    get() = tracksWithStatus.map { it.filter { it.value.state == SUCCEEDED }.keys }
  val failedTracks: LiveData<Set<Track>>
    get() = tracksWithStatus.map { it.filter { it.value.state == FAILED }.keys }

  val pendingTracksCount: LiveData<Int> get() = pendingTracks.map { it.size }
  val downloadingTracksCount: LiveData<Int> get() = downloadingTracks.map { it.size }
  val downloadedTracksCount: LiveData<Int> get() = downloadedTracks.map { it.size }
  val failedTracksCount: LiveData<Int> get() = failedTracks.map { it.size }
  val totalTracksCount: LiveData<Int> get() = tracksWithStatus.map { it.size }

  val totalProgress: LiveData<Float>
    get() = tracksWithStatus.map {
      it.map { it.value.progress.getFloat("progress", 0f) }.average().toFloat()
    }

  var tracksObserver: Observer<Set<Track>>? = null

  fun initDownloadStatuses(workManager: WorkManager) {
    if (tracksObserver != null) return

    tracksObserver = object : Observer<Set<Track>> {
      override fun onChanged(it: Set<Track>) {
        _tracksWithStatus.value = it.associate { track ->
          val workerTag = DownloadWorkerManager.tag(track.album, track)
          val info = workManager.getWorkInfosByTag(workerTag).get().last()
          track to info
        }
      }
    }

    tracks.observeForever(tracksObserver!!)
  }

  fun removeDownloadStatuses() {
    tracks.removeObserver(tracksObserver!!)
    tracksObserver = null
  }
}