package com.dorrin.harmonify.view.player

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RotatingVinylView(
  viewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  val track = viewModel.currentTrack.observeAsState()

  val trackTitle = track.value?.title
  val trackCover = track.value?.album?.coverBig

  val rotation = rememberInfiniteTransition()
    .animateFloat(
      0f,
      1f,
      infiniteRepeatable(
        tween(
          durationMillis = 10_000,
          delayMillis = 0,
          easing = LinearEasing
        ),
      )
    )

  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .requiredSize(300.dp)
      .clip(CircleShape)
  ) {
    Surface(
      color = Color.DarkGray,
      modifier = Modifier.requiredSize(300.dp)
    ) {}
    for (i in 1 until 5) {
      Surface(
        color = Color.Transparent,
        modifier = Modifier
          .fillMaxSize()
          .clip(CircleShape)
          .border(1.dp, Color.LightGray.copy(alpha = .9f), CircleShape)
          .requiredSize((300 - (i + 1) * 25).dp)
      ) {}
    }
    GlideImage(
      trackCover,
      contentDescription = trackTitle,
      contentScale = ContentScale.FillBounds,
      modifier = Modifier
        .requiredSize(150.dp)
        .clip(CircleShape)
        .graphicsLayer { rotationZ = rotation.value * 360f }
    )
  }
}