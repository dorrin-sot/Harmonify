package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.ShuffleOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun ShuffleIconButton(
  modifier: Modifier = Modifier,
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  val shuffleMode = playerViewModel.shuffleMode.observeAsState()

  val value = shuffleMode.value
  if (value != null) {
    IconButton(
      onClick = { playerViewModel.setShuffleMode(!value) },
      modifier = modifier
    ) {
      Icon(
        if (value) Icons.Default.ShuffleOn else Icons.Default.Shuffle,
        contentDescription = if (value) "Shuffle On" else "Shuffle Off",
      )
    }
  }
}