package com.dorrin.harmonify.service

import androidx.annotation.OptIn
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener.EventTime
import androidx.media3.exoplayer.analytics.PlaybackStats
import androidx.media3.exoplayer.analytics.PlaybackStatsListener
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.media3.session.MediaSession.MediaItemsWithStartPosition
import androidx.media3.session.MediaSessionService
import com.dorrin.harmonify.receiver.BedTimeReceiverStarter
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerService : MediaSessionService() {
  private var mediaSession: MediaSession? = null

  @Inject
  lateinit var bedTimeReceiverStarter: BedTimeReceiverStarter

  @OptIn(UnstableApi::class)
  override fun onCreate() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    super.onCreate()

    val player = ExoPlayer.Builder(this).build()
    player.addAnalyticsListener(EventLogger())
    mediaSession = MediaSession.Builder(this, player)
      .setCallback(object : MediaSession.Callback {
        @OptIn(UnstableApi::class)
        override fun onPlaybackResumption(
          mediaSession: MediaSession,
          controller: ControllerInfo
        ): ListenableFuture<MediaItemsWithStartPosition> {
          return SettableFuture.create<MediaItemsWithStartPosition>()
            .apply { set(MediaItemsWithStartPosition(emptyList(), 0, 0)) }
        }
      })
      .build()

    bedTimeReceiverStarter.start(this)
  }

  override fun onDestroy() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    bedTimeReceiverStarter.stop(this)
    mediaSession?.player?.release()
    mediaSession?.release()
    mediaSession = null
    super.onDestroy()
  }

  override fun onGetSession(controllerInfo: ControllerInfo): MediaSession? = mediaSession
}
