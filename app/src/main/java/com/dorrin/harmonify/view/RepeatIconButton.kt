package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOn
import androidx.compose.material.icons.filled.RepeatOneOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun RepeatIconButton(
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  val repeatMode by playerViewModel.repeatMode.observeAsState()

  repeatMode?.let {
    IconButton(onClick = { playerViewModel.setRepeatMode((it + 1) % 3) }) {
      Icon(
        when (it) {
          Player.REPEAT_MODE_ONE -> Icons.Default.RepeatOneOn
          Player.REPEAT_MODE_ALL -> Icons.Default.RepeatOn
          Player.REPEAT_MODE_OFF -> Icons.Default.Repeat
          else -> Icons.Default.Repeat
        },
        contentDescription =
          when (it) {
            Player.REPEAT_MODE_ONE -> "Repeat One"
            Player.REPEAT_MODE_ALL -> "Repeat All"
            Player.REPEAT_MODE_OFF -> "Repeat Off"
            else -> "Repeat Off"
          },
      )
    }
  }
}