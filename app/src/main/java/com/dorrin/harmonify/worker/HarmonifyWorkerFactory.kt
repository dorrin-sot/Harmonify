package com.dorrin.harmonify.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.dorrin.harmonify.apiservice.DownloadApiService
import com.dorrin.harmonify.permission.PermissionHandler
import com.dorrin.harmonify.repository.TrackLikeRepository
import javax.inject.Inject


@HiltWorker
class HarmonifyWorkerFactory @Inject constructor(
  private val trackLikeRepository: TrackLikeRepository,
  private val downloadApiService: DownloadApiService,
  private val permissionHandler: PermissionHandler
) : WorkerFactory() {
  override fun createWorker(
    appContext: Context,
    workerClassName: String,
    workerParameters: WorkerParameters
  ): ListenableWorker? {
    return when (workerClassName) {
      DownloadWorker::class.java.name -> DownloadWorker(
        appContext,
        workerParameters,
        trackLikeRepository,
        downloadApiService,
        permissionHandler
      )

      else -> null
    }
  }
}
