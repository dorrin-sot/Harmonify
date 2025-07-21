package com.dorrin.harmonify.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import com.dorrin.harmonify.service.PlayerService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AutoSleepReceiver : BroadcastReceiver() {

  init {
    println("${object {}.javaClass.enclosingClass?.name}::init")
  }

  override fun onReceive(context: Context, intent: Intent) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    val pauseIntent = Intent(context, PlayerService::class.java)
      .apply { action = PlayerService.ACTION_PAUSE_PLAYBACK }

    if (SDK_INT >= O) context.startForegroundService(pauseIntent)
    else context.startService(pauseIntent)
  }
}