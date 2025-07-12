package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun PlayerIconButton(
  track: Track? = null,
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  val isPlaying = playerViewModel.isPlaying.observeAsState()

  if (isPlaying.value == true) {
    PlayerPauseIconButton { playerViewModel.pause() }
  } else {
    PlayerResumeIconButton {
      if (track != null && !playerViewModel.isInQueue(track))
        playerViewModel.addToPlaylist(listOf(track))
      playerViewModel.play()
    }
  }
}

@Composable
private fun PlayerPauseIconButton(onPause: () -> Unit) {
  IconButton(onClick = onPause) {
    Icon(
      Icons.Default.Pause,
      contentDescription = "Pause"
    )
  }
}

@Composable
private fun PlayerResumeIconButton(onResume: () -> Unit) {
  IconButton(onClick = onResume) {
    Icon(
      Icons.Default.PlayArrow,
      contentDescription = "Play/Resume"
    )
  }
}
