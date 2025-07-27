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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.dorrin.harmonify.di.HarmonifyModuleProviders
import com.dorrin.harmonify.di.HarmonifyModuleProviders.providesChartService
import com.dorrin.harmonify.di.HarmonifyModuleProviders.providesTrackDao
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
  val track by viewModel.currentTrack.observeAsState()

  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(20.dp)
  ) {
    track?.let { track ->
      RotatingVinylView(viewModel)

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
      ) {
        Column(
          verticalArrangement = Arrangement.spacedBy(5.dp),
          modifier = Modifier.weight(1f)
        ) {
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
          )

          Text(
            track.artist.name,
            style = HarmonifyTypography.titleSmall,
            textAlign = TextAlign.Start,
            modifier = Modifier
              .fillMaxWidth()
              .basicMarquee()
          )
        }

        val isLiked by viewModel.currentTrackIsLiked.observeAsState(false)

        IconToggleButton(
          checked = isLiked,
          onCheckedChange = { viewModel.toggleLiked(track) },
        ) {
          Icon(
            if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isLiked) "Liked" else "Unliked"
          )
        }
      }

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
        val total by viewModel.currentTrackDurationMs.observeAsState(0L)
        val seek by viewModel.seek.observeAsState(0f)

        Text(
          duration(milliseconds = seek * total),
          style = HarmonifyTypography.labelSmall
        )
        Slider(
          value = seek,
          onValueChange = { viewModel.seekTo(it) },
          modifier = Modifier
            .padding(horizontal = 10.dp)
            .weight(1f)
        )
        Text(
          duration(milliseconds = total),
          style = HarmonifyTypography.labelMedium
        )
      }
    }
  }
}

private fun duration(milliseconds: Number): String =
  milliseconds
    .toInt()
    .milliseconds
    .toComponents { hours, minutes, seconds, _ ->
      "%02d:%02d:%02d".format(hours, minutes, seconds)
    }.let { if (it.startsWith("00:")) it.substring(3) else it }

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun PlayerViewPreview() {
  val exploreViewModel = ExploreViewModel(providesChartService())
  val context = LocalContext.current
  val playerViewModel = PlayerViewModel(context)

  exploreViewModel.chart
    .observeForever { playerViewModel.addToPlaylist(it.tracks.data) }

  PlayerView(playerViewModel)
}