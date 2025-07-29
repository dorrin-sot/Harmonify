package com.dorrin.harmonify.view.bottomsheet

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.work.WorkInfo.State
import androidx.work.WorkInfo.State.BLOCKED
import androidx.work.WorkInfo.State.CANCELLED
import androidx.work.WorkInfo.State.ENQUEUED
import androidx.work.WorkInfo.State.FAILED
import androidx.work.WorkInfo.State.RUNNING
import androidx.work.WorkInfo.State.SUCCEEDED
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dorrin.harmonify.entities.TrackLike
import com.dorrin.harmonify.extension.capitalize
import com.dorrin.harmonify.view.settings.item.StickyHeader
import com.dorrin.harmonify.viewmodel.BottomSheetType.DOWNLOADS_BOTTOM_SHEET
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel
import com.dorrin.harmonify.viewmodel.DownloadsViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun DownloadsBottomSheet(
  downloadsViewModel: DownloadsViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
  bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
) {
  val tracksWithStatus: List<TrackLike> by downloadsViewModel.tracks.observeAsState(emptyList())

  val tracks by remember {
    derivedStateOf {
      tracksWithStatus
        .groupBy { it.state }
        .mapNotNull { if (it.key == null) null else (it.key!! to it.value) }
        .toMap()
        .toSortedMap { o1, o2 -> o1.name.compareTo(o2.name) }
    }
  }

  ConditionalBottomSheet(
    type = DOWNLOADS_BOTTOM_SHEET,
    onExtraGet = {},
    bottomSheetViewModel,
  ) {
    LazyColumn {
      tracks.entries.forEach {
        val (state, tracks) = it

        item { StickyHeader(state.name.capitalize()) }

        items(tracks.toList(), key = { it.track.id }) { trackLike ->
          val track = trackLike.track

          ListItem(
            headlineContent = { Text(track.title) },
            supportingContent = { "${track.album.title} (${track.artist.name})" },
            leadingContent = {
              GlideImage(
                model = track.album.coverSmall,
                contentDescription = track.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                  .clip(CircleShape)
                  .shadow(2.dp, CircleShape),
              )
            },
            trailingContent = {
              StatefulCircularProgressIndicator(
                trackLike.state,
                trackLike.progress ?: 0f,
                onStart = { downloadsViewModel.start(track) },
                onStop = { downloadsViewModel.stop(track) }
              )
            },
          )
        }
      }
    }
  }
}

@Composable
fun StatefulCircularProgressIndicator(
  state: State?,
  progress: Float = 0f,
  onStart: () -> Unit,
  onStop: () -> Unit,
) {
  BadgedBox(
    badge = {
      if (state == RUNNING)
        Badge {
          Text("${progress.toInt() * 100}%")
        }
    }) {
    Box(contentAlignment = Alignment.Center) {
      if (state == RUNNING)
        CircularProgressIndicator(progress = { progress })
      else if (state == ENQUEUED)
        CircularProgressIndicator()


      when (state) {
        SUCCEEDED ->
          IconButton(onClick = {}, enabled = false) {
            Icon(Icons.Default.DownloadDone, "Download Done")
          }

        RUNNING, ENQUEUED, BLOCKED, null ->
          IconButton(onClick = onStop) {
            Icon(Icons.Default.Stop, "Stop")
          }

        FAILED, CANCELLED -> IconButton(onClick = onStart) {
          Icon(Icons.Default.Replay, "Replay")
        }
      }
    }
  }
}