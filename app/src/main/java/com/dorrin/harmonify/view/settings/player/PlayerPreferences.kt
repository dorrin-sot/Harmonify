package com.dorrin.harmonify.view.settings.player

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.minutes

interface PlayerPreferences {
  val autoSleepEnabled: Flow<Boolean>
  suspend fun setAutoSleepEnabled(value: Boolean? = null, commit: Boolean = true)

  val autoSleepDurationSec: Flow<Long>
  suspend fun setAutoSleepDurationSec(value: Long? = null, commit: Boolean = true)

  val dndEnabled: Flow<Boolean>
  suspend fun setDNDEnabled(value: Boolean? = null, commit: Boolean = true)

  companion object {
    const val KEY_AUTO_SLEEP_ENABLED = "auto_sleep_enabled"
    const val KEY_AUTO_SLEEP_DURATION_SEC = "auto_sleep_duration_sec"
    const val KEY_DND_ENABLED = "dnd_enabled"

    const val DEFAULT_AUTO_SLEEP_ENABLED = false
    val DEFAULT_AUTO_SLEEP_DURATION_SEC = 30.minutes.inWholeSeconds
    const val DEFAULT_DND_ENABLED = false
  }
}