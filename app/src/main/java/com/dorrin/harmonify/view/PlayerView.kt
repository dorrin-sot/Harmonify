package com.dorrin.harmonify.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerView() {
  val viewModel = hiltViewModel<PlayerViewModel>()

  val track = viewModel.currentTrack.observeAsState()

  val trackTitle = track.value?.title
  val trackCover = track.value?.album?.coverBig

  val seek = viewModel.seek.observeAsState()

  Column(
    verticalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxSize()
  ) {
    if (trackCover != null)
      GlideImage(
        trackCover,
        contentDescription = trackTitle,
        modifier = Modifier
          .padding(20.dp),
      )

    if (trackTitle != null)
      Text(trackTitle)

    Slider(
      value = seek.value ?: 0f,
      onValueChange = { viewModel.seekTo(it) },
    )
  }
}