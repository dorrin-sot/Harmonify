package com.dorrin.harmonify.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.worker.DownloadWorker.Companion.KEY_DOWNLOAD_URL
import com.dorrin.harmonify.worker.DownloadWorker.Companion.KEY_OUTPUT_FILE
import com.dorrin.harmonify.worker.DownloadWorker.Companion.KEY_SONG_NAME

interface DownloadWorkerManager<T> {
  fun schedule(item: T)
  fun cleanup(item: T)

  fun scheduleDownloadWorker(
    tag: String,
    downloadUrl: String,
    outputFile: String,
    songName: String,
    context: Context
  ) {
    val constraints = Constraints.Builder()
      .setRequiresStorageNotLow(true)
      .setRequiresBatteryNotLow(true)
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
      .addTag(tag)
      .setConstraints(constraints)
      .setInputData(
        workDataOf(
          KEY_DOWNLOAD_URL to downloadUrl,
          KEY_OUTPUT_FILE to outputFile,
          KEY_SONG_NAME to songName,
        )
      )
      .build()

    WorkManager.getInstance(context).enqueue(workRequest)
  }

  companion object {
    fun tag(album: Album, track: Track): String = "track_download_${album.id}/${track.id}"

    fun outputFilePath(rootDir: String, album: Album, track: Track): String =
      "${rootDir}/${album.id}/${track.id}"
  }
}