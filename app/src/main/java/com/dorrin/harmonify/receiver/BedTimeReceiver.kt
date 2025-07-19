package com.dorrin.harmonify.receiver

import android.app.NotificationManager
import android.app.NotificationManager.ACTION_INTERRUPTION_FILTER_CHANGED
import android.app.NotificationManager.INTERRUPTION_FILTER_ALARMS
import android.app.NotificationManager.INTERRUPTION_FILTER_PRIORITY
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import com.dorrin.harmonify.view.settings.player.PlayerPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BedTimeReceiver : BroadcastReceiver() {
  @Inject
  lateinit var playerPreferences: PlayerPreferences

  private val scope = CoroutineScope(Dispatchers.IO)

  init {
    println("${object {}.javaClass.enclosingClass?.name}::init")
  }

  override fun onReceive(context: Context, intent: Intent) {
    val manager = context.applicationContext
      .getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    object {}.javaClass.apply {
      println(
        "${enclosingClass?.name}::${enclosingMethod?.name} - " +
            "action: ${intent.action}, " +
            "currentInterruptionFilter: ${manager.currentInterruptionFilter}, "
      )
    }

    scope.launch {
      if (intent.action == ACTION_INTERRUPTION_FILTER_CHANGED) {
        playerPreferences.setDNDEnabled(
          when (manager.currentInterruptionFilter) {
            INTERRUPTION_FILTER_PRIORITY, INTERRUPTION_FILTER_ALARMS -> true
            else -> false
          }
        )
      }
    }
  }
}