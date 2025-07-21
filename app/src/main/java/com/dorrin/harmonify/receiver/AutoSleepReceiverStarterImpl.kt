package com.dorrin.harmonify.receiver

import android.app.AlarmManager
import android.app.AlarmManager.ELAPSED_REALTIME_WAKEUP
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.S
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.dorrin.harmonify.view.settings.player.PlayerPreferences
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class AutoSleepReceiverStarterImpl @Inject constructor(
  private val playerPreferences: PlayerPreferences
) : AutoSleepReceiverStarter {
  private var autoSleepReceiver: AutoSleepReceiver? = null

  private var pendingIntent: PendingIntent? = null

  private val autoSleepDurationMS: LiveData<Long>
    get() = playerPreferences.autoSleepDurationSec
      .map { it.toInt().seconds.inWholeMilliseconds }
      .asLiveData()

  override fun start(context: Context) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    alarmManager ?: return

    if (SDK_INT >= S && alarmManager.canScheduleExactAlarms() || SDK_INT < S)
      startAlarm(context, alarmManager)
  }

  private fun startAlarm(context: Context, alarmManager: AlarmManager) {
    pendingIntent = Intent(context, AutoSleepReceiver::class.java).let { intent ->
      PendingIntent.getBroadcast(
        context,
        0,
        intent,
        FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
      )
    }

    alarmManager.setExact(
      ELAPSED_REALTIME_WAKEUP,
      autoSleepDurationMS.value ?: 0,
      pendingIntent!!
    )
  }

  override fun stop(context: Context) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    alarmManager ?: return

    autoSleepReceiver?.let {
      pendingIntent ?: return
      alarmManager.cancel(pendingIntent!!)
      pendingIntent = null
      autoSleepReceiver = null
    }
  }
}