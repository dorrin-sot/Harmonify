package com.dorrin.harmonify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.work.WorkManager
import com.dorrin.harmonify.ui.theme.HarmonifyTheme
import com.dorrin.harmonify.view.BaseView
import com.dorrin.harmonify.viewmodel.DownloadsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val downloadsViewModel by viewModels<DownloadsViewModel>()

  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      HarmonifyTheme {
        BaseView()
      }
    }
  }

  override fun onResume() {
    super.onResume()
    downloadsViewModel.initDownloadStatuses(WorkManager.getInstance(this))
  }

  override fun onPause() {
    super.onPause()
    downloadsViewModel.removeDownloadStatuses()
  }
}