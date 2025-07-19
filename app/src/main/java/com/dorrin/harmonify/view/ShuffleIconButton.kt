package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.ShuffleOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun ShuffleIconButton(
  modifier: Modifier = Modifier,
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  val shuffleMode by playerViewModel.shuffleMode.observeAsState(false)

  IconButton(
    onClick = { playerViewModel.setShuffleMode(!shuffleMode) },
    modifier = modifier
  ) {
    Icon(
      if (shuffleMode) Icons.Default.ShuffleOn else Icons.Default.Shuffle,
      contentDescription = if (shuffleMode) "Shuffle On" else "Shuffle Off",
    )
  }
}