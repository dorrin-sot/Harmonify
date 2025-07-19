package com.dorrin.harmonify.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.PlaylistRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@Composable
fun PlaylistAddRemoveIconButton(
  track: Track,
  modifier: Modifier = Modifier,
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  val modifier = Modifier
    .requiredSize(50.dp)
    .padding(5.dp)
    .then(modifier)

  var isInPlaylist by rememberSaveable { mutableStateOf(playerViewModel.isInQueue(track)) }

  if (!isInPlaylist) {
    PlaylistAddIconButton(modifier) {
      playerViewModel.run {
        addToPlaylist(listOf(track))
        isInPlaylist = true
      }
    }
  } else {
    PlaylistRemoveIconButton(modifier) {
      playerViewModel.removeFromPlaylist(listOf(track))
      isInPlaylist = true
    }
  }
}

@Composable
private fun PlaylistAddIconButton(
  modifier: Modifier = Modifier,
  onAdd: () -> Unit
) {
  IconButton(
    onClick = { onAdd() }, modifier = modifier
  ) {
    @Suppress("DEPRECATION")
    Icon(
      Icons.Default.PlaylistAdd,
      contentDescription = "Add to playlist"
    )
  }
}

@Composable
private fun PlaylistRemoveIconButton(
  modifier: Modifier = Modifier,
  onRemove: () -> Unit
) {
  IconButton(
    onClick = { onRemove() }, modifier = modifier
  ) {
    Icon(
      Icons.Default.PlaylistRemove,
      contentDescription = "Remove to playlist"
    )
  }
}
