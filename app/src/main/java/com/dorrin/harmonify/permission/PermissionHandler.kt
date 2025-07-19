package com.dorrin.harmonify.permission

import androidx.activity.ComponentActivity

interface PermissionHandler {
  fun handlePermission(
    activity: ComponentActivity,
    permission: String,
    onGranted: () -> Unit = {},
    onDenied: () -> Unit = {},
    rationale: String? = null,
  )
}