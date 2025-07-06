package com.dorrin.harmonify.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun PlayerIconButton(
  track: Track,
  onEnqueue: () -> Unit
) {
  val playerViewModel = hiltViewModel<PlayerViewModel>()

  val modifier = Modifier
    .requiredSize(25.dp)
    .padding(5.dp)

  if (playerViewModel.isCurrentInQueue(track)) {
    if (playerViewModel.isPlaying) {
      PlayerPauseIconButton(modifier) { playerViewModel.pause() }
    } else {
      PlayerResumeIconButton(modifier) { playerViewModel.resume() }
    }
  } else {
    PlayerPlayIconButton(modifier) {
      onEnqueue()
      playerViewModel.start(track)
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
      contentDescription = "Resume"
    )
  }
}

@Composable
private fun PlayerPlayIconButton(modifier: Modifier = Modifier, onPlay: () -> Unit) {
  IconButton(onClick = onPlay, modifier = modifier) {
    Icon(
      Icons.Default.PlayArrow,
      contentDescription = "Play"
    )
  }
}
