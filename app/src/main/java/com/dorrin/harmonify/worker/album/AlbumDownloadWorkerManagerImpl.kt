package com.dorrin.harmonify.worker.album

import android.content.Context
import androidx.work.WorkManager
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.worker.DownloadWorkerManager.Companion.outputFilePath
import com.dorrin.harmonify.worker.DownloadWorkerManager.Companion.tag
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class AlbumDownloadWorkerManagerImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : AlbumDownloadWorkerManager {
  override fun schedule(album: Album) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    album.tracks?.data?.forEach { track ->
      scheduleDownloadWorker(
        tag = tag(album, track),
        downloadUrl = track.preview,
        outputFile = outputFilePath(context.filesDir.absolutePath, album, track),
        songName = track.title,
        context = context
      )
    }
  }

  override fun cleanup(album: Album) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    album.tracks?.data?.forEach { track ->
      WorkManager.getInstance(context).cancelAllWorkByTag(tag(album, track))
      File(
        outputFilePath(
          context.filesDir.absolutePath,
          album,
          track
        )
      ).run { if (exists()) delete() }
    }
  }
}
