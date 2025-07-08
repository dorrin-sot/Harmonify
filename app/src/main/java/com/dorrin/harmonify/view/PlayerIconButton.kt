package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun PlayerIconButton(modifier: Modifier = Modifier, track: Track? = null) {
  val playerViewModel = hiltViewModel<PlayerViewModel>(LocalActivity.current as ComponentActivity)

  val modifier = Modifier
    .requiredSize(50.dp)
    .padding(5.dp)
    .then(modifier)

  val isPlaying = playerViewModel.isPlaying.observeAsState()

  if (isPlaying.value == true) {
    PlayerPauseIconButton(modifier) { playerViewModel.pause() }
  } else {
    PlayerResumeIconButton(modifier) {
      if (track != null && !playerViewModel.isInQueue(track))
        playerViewModel.addToPlaylist(listOf(track))
      playerViewModel.play()
    }
  }
}

@Composable
private fun PlayerPauseIconButton(modifier: Modifier = Modifier, onPause: () -> Unit) {
  IconButton(onClick = onPause, modifier = modifier) {
    Icon(
      Icons.Default.Pause,
      contentDescription = "Pause"
    )
  }
}

@Composable
private fun PlayerResumeIconButton(modifier: Modifier = Modifier, onResume: () -> Unit) {
  IconButton(onClick = onResume, modifier = modifier) {
    Icon(
      Icons.Default.PlayArrow,
      contentDescription = "Play/Resume"
    )
  }
}
