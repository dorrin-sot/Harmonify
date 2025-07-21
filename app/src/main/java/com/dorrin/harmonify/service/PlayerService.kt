package com.dorrin.harmonify.service

import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSession.ControllerInfo
import androidx.media3.session.MediaSession.MediaItemsWithStartPosition
import androidx.media3.session.MediaSessionService
import com.dorrin.harmonify.receiver.AutoSleepReceiverStarter
import com.dorrin.harmonify.receiver.BedTimeReceiverStarter
import com.dorrin.harmonify.view.settings.player.PlayerPreferences
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@AndroidEntryPoint
class PlayerService : MediaSessionService() {
  private var mediaSession: MediaSession? = null

  @Inject
  lateinit var bedTimeReceiverStarter: BedTimeReceiverStarter

  @Inject
  lateinit var autoSleepReceiverStarter: AutoSleepReceiverStarter

  @Inject
  lateinit var playerPreferences: PlayerPreferences

  @kotlin.OptIn(FlowPreview::class)
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

    val dndEnabled = playerPreferences.dndEnabled.debounce(5.seconds)
    val autoSleepEnabled = playerPreferences.autoSleepEnabled.debounce(5.seconds)
    CoroutineScope(Dispatchers.Main).launch {
      dndEnabled
        .combineTransform(autoSleepEnabled) { it1, it2 -> emit(it1 && it2) }
        .collect { enabled ->
          if (enabled) autoSleepReceiverStarter.start(this@PlayerService)
          else autoSleepReceiverStarter.stop(this@PlayerService)
        }
    }
  }

  override fun onDestroy() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    autoSleepReceiverStarter.stop(this)
    bedTimeReceiverStarter.stop(this)
    mediaSession?.player?.release()
    mediaSession?.release()
    mediaSession = null
    super.onDestroy()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    super.onStartCommand(intent, flags, startId)

    when (intent?.action) {
      ACTION_PAUSE_PLAYBACK -> mediaSession?.player?.pause()
    }

    return START_NOT_STICKY
  }


  override fun onGetSession(controllerInfo: ControllerInfo): MediaSession? = mediaSession

  companion object {
    const val ACTION_PAUSE_PLAYBACK = "com.dorrin.harmonify.action.PAUSE_PLAYBACK"
  }
}
