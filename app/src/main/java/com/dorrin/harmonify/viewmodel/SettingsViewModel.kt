package com.dorrin.harmonify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.view.settings.player.PlayerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val playerPrefs: PlayerPreferences,
) : ViewModel() {
  val autoSleepEnabled: LiveData<Boolean> = playerPrefs.autoSleepEnabled.asLiveData()
  val autoSleepDurationSec: LiveData<Long> = playerPrefs.autoSleepDurationSec.asLiveData()

  fun setAutoSleepEnabled(autoSleepEnabled: Boolean) {
    viewModelScope.launch {
      playerPrefs.setAutoSleepEnabled(autoSleepEnabled)
    }
  }

  fun setAutoSleepDurationSec(autoSleepDurationSec: Long) {
    viewModelScope.launch {
      playerPrefs.setAutoSleepDurationSec(autoSleepDurationSec)
    }
  }
}