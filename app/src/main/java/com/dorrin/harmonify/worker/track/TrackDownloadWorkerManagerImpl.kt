package com.dorrin.harmonify.worker.track

import android.content.Context
import androidx.work.WorkManager
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.worker.DownloadWorkerManager.Companion.outputFilePath
import com.dorrin.harmonify.worker.DownloadWorkerManager.Companion.tag
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class TrackDownloadWorkerManagerImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : TrackDownloadWorkerManager {

  override fun schedule(track: Track) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    scheduleDownloadWorker(
      tag = tag(track.album, track),
      downloadUrl = track.preview,
      outputFile = outputFilePath(context.filesDir.absolutePath, track.album, track),
      songName = track.title,
      context = context
    )
  }

  override fun cleanup(track: Track) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    WorkManager.getInstance(context).cancelAllWorkByTag("track_download_${track.id}")

    File(
      outputFilePath(
        context.filesDir.absolutePath,
        track.album,
        track
      )
    ).run { if (exists()) delete() }
  }
}