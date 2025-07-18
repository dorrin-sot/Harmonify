package com.dorrin.harmonify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.dorrin.harmonify.ui.theme.HarmonifyTheme
import com.dorrin.harmonify.view.BaseView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
}