package com.dorrin.harmonify.provider

import androidx.media3.session.MediaController
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Singleton
class MediaControllerProvider @Inject constructor(
  val controllerFuture: ListenableFuture<MediaController>
) {
  fun getController(): MediaController? =
    if (controllerFuture.isDone) {
      try {
        controllerFuture.get()
      } catch (e: Exception) {
        null
      }
    } else {
      null
    }

  suspend fun awaitController(): MediaController =
    suspendCoroutine { continuation ->
      controllerFuture.addListener({
        try {
          continuation.resume(controllerFuture.get())
        } catch (e: Exception) {
          continuation.resumeWithException(e)
        }
      }, MoreExecutors.directExecutor())
    }
}