package com.dorrin.harmonify.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.dorrin.harmonify.apiservice.DownloadApiService
import com.dorrin.harmonify.permission.PermissionHandler
import javax.inject.Inject


@HiltWorker
class HarmonifyWorkerFactory @Inject constructor(
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
        downloadApiService,
        permissionHandler
      )

      else -> null
    }
  }
}
