package com.dorrin.harmonify.view.settings.library

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.minutes

interface LibraryPreferences {
  val autoDownloadEnabled: Flow<Boolean>
  suspend fun setAutoDownloadEnabled(value: Boolean? = null, commit: Boolean = true)

  companion object {
    const val KEY_AUTO_DOWNLOAD_ENABLED = "auto_download_enabled"

    const val DEFAULT_AUTO_DOWNLOAD_ENABLED = false
  }
}