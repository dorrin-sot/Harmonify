package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.ui.compose.PlayerSurface
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerView() {
  val viewModel = hiltViewModel<PlayerViewModel>(LocalActivity.current as ComponentActivity)

//  val track = viewModel.currentTrack.observeAsState()
//
//  val trackTitle = track.value?.title
//
//  val seek = viewModel.seek.observeAsState()
//
//  Column(
//    verticalArrangement = Arrangement.Center,
//    horizontalAlignment = Alignment.CenterHorizontally,
//    modifier = Modifier
//      .fillMaxSize()
//      .padding(20.dp)
//  ) {
//    RotatingVinylView()
//
//    Text(trackTitle ?: "")
//
//    Row(
//      horizontalArrangement = Arrangement.Center,
//      verticalAlignment = Alignment.CenterVertically
//    ) {
//      ShuffleIconButton()
//
//      IconButton(
//        { viewModel.skipBackward(10) },
//      ) { Icon(Icons.Default.Replay10, "Skip backward 10s") }
//
//      IconButton(
//        { viewModel.skipPrevious() },
//        enabled = viewModel.canSkipPrevious
//      ) { Icon(Icons.Default.SkipPrevious, "Skip Previous") }
//
//      PlayerIconButton(
//        modifier = Modifier.requiredSize(75.dp)
//      )
//
//      IconButton(
//        { viewModel.skipNext() },
//        enabled = viewModel.canSkipNext
//      ) { Icon(Icons.Default.SkipNext, "Skip Next") }
//
//      IconButton(
//        { viewModel.skipForward(10) },
//      ) { Icon(Icons.Default.Forward10, "Skip forward 10s") }
//
//      RepeatIconButton()
//    }
//
//    Slider(
//      value = seek.value ?: 0f,
//      onValueChange = { viewModel.seekTo(it) },
//    )
//  }
}