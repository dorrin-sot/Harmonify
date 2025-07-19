package com.dorrin.harmonify.provider

import android.content.ComponentName
import android.content.Context
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.dorrin.harmonify.service.PlayerService
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MediaControllerProvider @Inject constructor(
  @ApplicationContext context: Context
) {
  private val controllerFuture =
    MediaController.Builder(
      context,
      SessionToken(context, ComponentName(context, PlayerService::class.java))
    ).buildAsync()

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