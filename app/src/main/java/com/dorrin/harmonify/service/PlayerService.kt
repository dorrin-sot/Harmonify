package com.dorrin.harmonify.service

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.media3.session.MediaSession.MediaItemsWithStartPosition
import androidx.media3.session.MediaSessionService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture

class PlayerService : MediaSessionService() {
  private var mediaSession: MediaSession? = null

  override fun onCreate() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    super.onCreate()

    val player = ExoPlayer.Builder(this).build()
    mediaSession = MediaSession.Builder(this, player)
      .setCallback(object : MediaSession.Callback {
        @OptIn(UnstableApi::class)
        override fun onPlaybackResumption(
          mediaSession: MediaSession,
          controller: ControllerInfo
        ): ListenableFuture<MediaItemsWithStartPosition> {
          object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
          return SettableFuture.create<MediaItemsWithStartPosition>()
            .apply { set(MediaItemsWithStartPosition(emptyList(), 0, 0)) }
        }
      })
      .build()
  }

  override fun onDestroy() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    mediaSession?.player?.release()
    mediaSession?.release()
    mediaSession = null
    super.onDestroy()
  }

  override fun onGetSession(controllerInfo: ControllerInfo): MediaSession? = mediaSession
}
