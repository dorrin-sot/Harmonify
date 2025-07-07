package com.dorrin.harmonify.service

import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.media3.session.MediaSessionService

class PlayerService : MediaSessionService() {
  private var mediaSession: MediaSession? = null

  override fun onCreate() {
    super.onCreate()

    val player = ExoPlayer.Builder(this).build()
    mediaSession = MediaSession.Builder(this, player).build()
  }

  override fun onDestroy() {
    mediaSession?.player?.release()
    mediaSession?.release()
    mediaSession = null
    super.onDestroy()
  }

  override fun onGetSession(controllerInfo: ControllerInfo): MediaSession? = mediaSession
}
