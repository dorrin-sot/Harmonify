package com.dorrin.harmonify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dorrin.harmonify.ui.theme.HarmonifyTheme
import com.dorrin.harmonify.view.BaseView
import com.dorrin.harmonify.viewmodel.ExploreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val exploreViewModel by viewModels<ExploreViewModel>()

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

  override fun onStart() {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }
    super.onStart()
    exploreViewModel.getChart()
  }
}