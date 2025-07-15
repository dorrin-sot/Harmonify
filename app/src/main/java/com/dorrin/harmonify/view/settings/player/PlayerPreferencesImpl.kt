package com.dorrin.harmonify.view.settings.player

import com.dorrin.harmonify.view.settings.PreferenceManager
import com.dorrin.harmonify.view.settings.player.PlayerPreferences.Companion.DEFAULT_AUTO_SLEEP_DURATION_SEC
import com.dorrin.harmonify.view.settings.player.PlayerPreferences.Companion.DEFAULT_AUTO_SLEEP_ENABLED
import com.dorrin.harmonify.view.settings.player.PlayerPreferences.Companion.KEY_AUTO_SLEEP_DURATION_SEC
import com.dorrin.harmonify.view.settings.player.PlayerPreferences.Companion.KEY_AUTO_SLEEP_ENABLED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PlayerPreferencesImpl @Inject constructor() : PlayerPreferences {
  @Inject
  internal lateinit var prefManager: PreferenceManager

  private var _autoSleepEnabled = MutableStateFlow<Boolean>(DEFAULT_AUTO_SLEEP_ENABLED)
  private var _autoSleepDurationSec = MutableStateFlow<Long>(DEFAULT_AUTO_SLEEP_DURATION_SEC)

  override val autoSleepEnabled: Flow<Boolean> = _autoSleepEnabled
  override val autoSleepDurationSec: Flow<Long> = _autoSleepDurationSec

  override suspend fun setAutoSleepEnabled(value: Boolean?, commit: Boolean) =
    prefManager.update(
      key = KEY_AUTO_SLEEP_ENABLED,
      value = value,
      default = DEFAULT_AUTO_SLEEP_ENABLED,
      commit = commit,
      flow = _autoSleepEnabled
    )

  override suspend fun setAutoSleepDurationSec(value: Long?, commit: Boolean) =
    prefManager.update(
      key = KEY_AUTO_SLEEP_DURATION_SEC,
      value = value,
      default = DEFAULT_AUTO_SLEEP_DURATION_SEC,
      commit = commit,
      flow = _autoSleepDurationSec
    )
}
