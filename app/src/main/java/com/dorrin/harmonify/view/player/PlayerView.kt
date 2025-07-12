package com.dorrin.harmonify.view.player

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.dorrin.harmonify.HarmonifyModule
import com.dorrin.harmonify.provider.MediaControllerProvider
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import com.dorrin.harmonify.view.PlayerIconButton
import com.dorrin.harmonify.view.RepeatIconButton
import com.dorrin.harmonify.view.ShuffleIconButton
import com.dorrin.harmonify.viewmodel.ExploreViewModel
import com.dorrin.harmonify.viewmodel.PlayerViewModel
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PlayerView(
  viewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  val track = viewModel.currentTrack.observeAsState()

  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(20.dp)
  ) {
    if (track.value != null) {
      val track = track.value!!
      RotatingVinylView(viewModel)

      Text(
        track.title,
        style = HarmonifyTypography.titleLarge,
        textAlign = TextAlign.Start,
        modifier = Modifier
          .fillMaxWidth()
          .basicMarquee(
            initialDelayMillis = 2000,
            repeatDelayMillis = 1500
          )
          .padding(top = 25.dp)
          .padding(horizontal = 10.dp)
      )

      Text(
        track.artist.name,
        style = HarmonifyTypography.titleSmall,
        textAlign = TextAlign.Start,
        modifier = Modifier
          .fillMaxWidth()
          .basicMarquee()
          .padding(bottom = 10.dp)
          .padding(horizontal = 10.dp)
      )

      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        ShuffleIconButton(playerViewModel = viewModel)

        IconButton(
          { viewModel.skipForward(-10) },
        ) { Icon(Icons.Default.Replay10, "Skip backward 10s") }

        IconButton(
          { viewModel.skipPrevious() },
          enabled = viewModel.canSkipPrevious
        ) { Icon(Icons.Default.SkipPrevious, "Skip Previous") }

        PlayerIconButton(playerViewModel = viewModel)

        IconButton(
          { viewModel.skipNext() },
          enabled = viewModel.canSkipNext
        ) { Icon(Icons.Default.SkipNext, "Skip Next") }

        IconButton(
          { viewModel.skipForward(10) },
        ) { Icon(Icons.Default.Forward10, "Skip forward 10s") }

        RepeatIconButton(playerViewModel = viewModel)
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        val total = viewModel.currentTrackDurationMs.observeAsState()
        val seek = viewModel.seek.observeAsState()

        Text(
          duration(seek.value?.times(total.value?.toFloat() ?: 0f)),
          style = HarmonifyTypography.labelSmall
        )
        Slider(
          value = seek.value ?: 0f,
          onValueChange = { viewModel.seekTo(it) },
          modifier = Modifier
            .padding(horizontal = 10.dp)
            .weight(1f)
        )
        Text(
          duration(total.value),
          style = HarmonifyTypography.labelMedium
        )
      }
    }
  }
}

private fun duration(seconds: Number?): String =
  seconds
    ?.toInt()
    ?.milliseconds
    ?.toComponents { hours, minutes, seconds, _ ->
      "%02d:%02d:%02d".format(hours, minutes, seconds)
    }?.let { if (it.startsWith("00:")) it.substring(3) else it }
    ?: ""


@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun PlayerViewPreview() {
  val exploreViewModel = ExploreViewModel(HarmonifyModule.providesChartService(),)
  val playerViewModel = PlayerViewModel(
    MediaControllerProvider(
      HarmonifyModule.providesMediaController(LocalContext.current)
    )
  )

  exploreViewModel.chart
    .observeForever { chart ->
      playerViewModel.addToPlaylist(chart?.tracks?.data ?: emptyList())
      playerViewModel.play()
    }

  PlayerView(playerViewModel)
}