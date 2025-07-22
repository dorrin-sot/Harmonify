package com.dorrin.harmonify.view.settings.library

import com.dorrin.harmonify.view.settings.PreferenceManager
import com.dorrin.harmonify.view.settings.library.LibraryPreferences.Companion.DEFAULT_AUTO_DOWNLOAD_ENABLED
import com.dorrin.harmonify.view.settings.library.LibraryPreferences.Companion.KEY_AUTO_DOWNLOAD_ENABLED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class LibraryPreferencesImpl @Inject constructor(
  internal val prefManager: PreferenceManager
) : LibraryPreferences {
  private var _autoDownloadEnabled =
    MutableStateFlow(prefManager.get(KEY_AUTO_DOWNLOAD_ENABLED, DEFAULT_AUTO_DOWNLOAD_ENABLED))

  override val autoDownloadEnabled: Flow<Boolean> get() = _autoDownloadEnabled

  override suspend fun setAutoDownloadEnabled(value: Boolean?, commit: Boolean) =
    prefManager.update(
      key = KEY_AUTO_DOWNLOAD_ENABLED,
      value = value,
      default = DEFAULT_AUTO_DOWNLOAD_ENABLED,
      commit = commit,
      flow = _autoDownloadEnabled
    )
}