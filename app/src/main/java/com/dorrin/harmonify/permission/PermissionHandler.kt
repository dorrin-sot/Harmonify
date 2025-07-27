package com.dorrin.harmonify.permission

import android.content.Context
import androidx.activity.ComponentActivity

interface PermissionHandler {
  fun hasPermission(context: Context, permission: String): Boolean

  fun handlePermission(
    activity: ComponentActivity,
    permission: String,
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {},
    rationale: String? = null,
  )
}