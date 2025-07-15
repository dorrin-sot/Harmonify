package com.dorrin.harmonify.view.settings

import kotlinx.coroutines.flow.MutableStateFlow

interface PreferenceManager {
  suspend fun <T> update(
    key: String,
    value: T?,
    default: T,
    commit: Boolean = true,
    flow: MutableStateFlow<T>? = null
  )

  fun <T> get(key: String, default: T): T
}
