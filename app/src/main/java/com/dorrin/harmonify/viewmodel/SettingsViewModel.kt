package com.dorrin.harmonify.viewmodel

import android.Manifest.permission.ACCESS_NOTIFICATION_POLICY
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dorrin.harmonify.permission.PermissionHandler
import com.dorrin.harmonify.view.settings.library.LibraryPreferences
import com.dorrin.harmonify.view.settings.player.PlayerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val playerPrefs: PlayerPreferences,
  private val libraryPrefs: LibraryPreferences,
  private val permissionHandler: PermissionHandler,
) : ViewModel() {
  val autoSleepEnabled: LiveData<Boolean> = playerPrefs.autoSleepEnabled.asLiveData()
  val autoSleepDurationSec: LiveData<Long> = playerPrefs.autoSleepDurationSec.asLiveData()
  val autoDownloadEnabled: LiveData<Boolean> = libraryPrefs.autoDownloadEnabled.asLiveData()

  fun setAutoSleepEnabled(activity: ComponentActivity, autoSleepEnabled: Boolean) {
    if (autoSleepEnabled)
      permissionHandler.handlePermission(
        activity,
        permission = ACCESS_NOTIFICATION_POLICY,
        onGranted = { viewModelScope.launch { playerPrefs.setAutoSleepEnabled(autoSleepEnabled) } },
        onDenied = {
          Toast.makeText(
            activity,
            "The auto-sleep feature requires Do not disturb status permission.",
            Toast.LENGTH_LONG
          ).show()
          viewModelScope.launch { playerPrefs.setAutoSleepEnabled(false) }
        },
      ) else viewModelScope.launch { playerPrefs.setAutoSleepEnabled(autoSleepEnabled) }
  }

  fun setAutoSleepDurationSec(autoSleepDurationSec: Long) {
    viewModelScope.launch {
      playerPrefs.setAutoSleepDurationSec(autoSleepDurationSec)
    }
  }

  fun setAutoDownloadEnabled(autoDownloadEnabled: Boolean) {
    viewModelScope.launch { libraryPrefs.setAutoDownloadEnabled(autoDownloadEnabled) }
  }
}