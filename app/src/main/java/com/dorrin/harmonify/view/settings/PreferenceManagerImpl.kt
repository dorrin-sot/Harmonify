package com.dorrin.harmonify.view.settings

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PreferenceManagerImpl @Inject constructor() : PreferenceManager {
  @Inject
  internal lateinit var sharedPrefs: SharedPreferences

  override suspend fun <T> update(
    key: String,
    value: T?,
    default: T,
    commit: Boolean,
    flow: MutableStateFlow<T>?
  ) {
    sharedPrefs.edit().run {
      if (value == null) remove(key)
      else put(key, value)

      if (commit) apply()
    }
    flow?.emit(sharedPrefs.get(key, default))
  }

  private fun <T> SharedPreferences.Editor.put(key: String, value: T) {
    if (value is Boolean) putBoolean(key, value)
    else if (value is Int) putInt(key, value)
    else if (value is Long) putLong(key, value)
    else if (value is Float) putFloat(key, value)
    else if (value is String) putString(key, value)
    else throw IllegalArgumentException()
  }

  private fun <T> SharedPreferences.get(key: String, default: T): T = (
      if (default is Boolean) getBoolean(key, default)
      else if (default is Int) getInt(key, default)
      else if (default is Long) getLong(key, default)
      else if (default is Float) getFloat(key, default)
      else if (default is String) getString(key, default)
      else throw IllegalArgumentException()
      ) as T
}
