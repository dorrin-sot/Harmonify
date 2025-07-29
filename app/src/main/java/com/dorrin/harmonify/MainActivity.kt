package com.dorrin.harmonify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.ui.theme.HarmonifyTheme
import com.dorrin.harmonify.view.BaseView
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel
import com.dorrin.harmonify.viewmodel.DownloadsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val bottomSheetViewModel by viewModels<BottomSheetViewModel>()
  private val downloadsViewModel by viewModels<DownloadsViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      HarmonifyTheme {
        BaseView(
          bottomSheetViewModel,
          downloadsViewModel
        )
      }
    }
  }
}