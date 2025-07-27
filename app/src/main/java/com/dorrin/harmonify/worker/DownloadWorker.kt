package com.dorrin.harmonify.worker

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.dorrin.harmonify.R
import com.dorrin.harmonify.apiservice.DownloadApiService
import com.dorrin.harmonify.permission.PermissionHandler
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File
import java.io.FileOutputStream


@HiltWorker
class DownloadWorker @AssistedInject constructor(
  @Assisted private val context: Context,
  @Assisted private val workerParams: WorkerParameters,
  private val downloadApiService: DownloadApiService,
  private val permissionHandler: PermissionHandler
) : CoroutineWorker(context, workerParams) {
  override suspend fun doWork(): Result {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    val downloadUrl = inputData.getString(KEY_DOWNLOAD_URL) ?: return Result.failure()
    val outputPath = inputData.getString(KEY_OUTPUT_FILE) ?: return Result.failure()
    val songName = inputData.getString(KEY_SONG_NAME) ?: return Result.failure()

    val response = downloadApiService.downloadFile(downloadUrl)

    if (response.isSuccessful) {
      response.body()?.let { body ->
        val total = body.contentLength()

        val pathname = "${outputPath}.${body.contentType()!!.subtype}"
        val outputFile = File(pathname).apply {
          if (!exists()) {
            parentFile?.mkdirs()
            createNewFile()
          }
        }
        val inputStream = body.byteStream()
        val outputStream = FileOutputStream(outputFile)

        val buffer = ByteArray(8 * 1024)
        var bytesRead: Int
        var downloaded = 0L

        val notificationId = outputFile.hashCode()
        val notificationBuilder = NotificationCompat.Builder(applicationContext, "download_channel")
          .setSmallIcon(R.drawable.ic_download_for_offline)
          .setContentTitle("Downloading $songName")
          .setProgress(100, 0, false)
          .setOnlyAlertOnce(true)
          .setOngoing(true)

        val manager = NotificationManagerCompat.from(applicationContext)

        val hasPermission = permissionHandler.hasPermission(context, POST_NOTIFICATIONS)
        if (hasPermission) manager.notify(notificationId, notificationBuilder.build())

        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
          outputStream.write(buffer, 0, bytesRead)
          downloaded += bytesRead

          val progress = (100 * downloaded / total).toInt()
          setProgress(
            workDataOf(
              "downloaded" to downloaded,
              "total" to total,
              "progress" to progress
            )
          )

          notificationBuilder.setProgress(100, progress, false)
          if (hasPermission) manager.notify(notificationId, notificationBuilder.build())
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()

        notificationBuilder.setContentText("Download complete")
          .setProgress(0, 0, false)
          .setOngoing(false)
        if (hasPermission) manager.notify(notificationId, notificationBuilder.build())

        return Result.success(workDataOf("outputPath" to outputFile.absolutePath))
      }
    }

    return Result.failure()
  }

  companion object {
    const val KEY_DOWNLOAD_URL = "download_url"
    const val KEY_OUTPUT_FILE = "file_name"
    const val KEY_SONG_NAME = "song_name"
  }
}
