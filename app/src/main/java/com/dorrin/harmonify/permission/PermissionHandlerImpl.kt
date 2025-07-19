package com.dorrin.harmonify.permission

import android.content.pm.PackageManager.PERMISSION_DENIED
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat.checkSelfPermission
import javax.inject.Inject

class PermissionHandlerImpl @Inject constructor() : PermissionHandler {
  override fun handlePermission(
    activity: ComponentActivity,
    permission: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit,
    rationale: String?
  ) {
    if (checkSelfPermission(activity, permission) == PERMISSION_DENIED) {
      if (shouldShowRequestPermissionRationale(activity, permission)) {
        TODO("Show rationale for this permission $rationale")
      }

      val launcher = activity.registerForActivityResult(RequestPermission()) { granted ->
        if (!granted) onDenied()
        else onGranted()
      }
      launcher.launch(permission)
    } else
      onGranted()
  }
}