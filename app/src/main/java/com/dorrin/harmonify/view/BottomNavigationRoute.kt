package com.dorrin.harmonify.view

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import com.dorrin.harmonify.extension.capitalize
import com.dorrin.harmonify.view.downloads.DownloadsIconButton
import com.dorrin.harmonify.view.player.PlayerView
import com.dorrin.harmonify.view.search.SearchIconButton
import com.dorrin.harmonify.view.settings.SettingsView

enum class BottomNavigationRoute(
  val icon: ImageVector,
  val content: @Composable () -> Unit
) {
  SETTINGS(icon = Icons.Default.Settings, content = { SettingsView() }),
  EXPLORE(icon = Icons.Default.Star, content = { ExploreView() }),
  LIBRARY(icon = Icons.Default.LibraryMusic, content = { LibraryView() }),
  PLAYER(icon = Icons.Default.PlayArrow, content = { PlayerView() });

  internal val route: String
    get() = "/${name.lowercase()}"

  internal val title: String
    get() = name.replace("_", " ").capitalize()

  @Composable
  private fun TopBarActions(
    totalTracksCount: Int,
    downloadingTracksCount: Int,
    totalProgress: Float,
    onDownloadsIconButtonClicked: () -> Unit
  ) {
    if (route == LIBRARY.route) {
      LaunchedEffect(totalTracksCount, downloadingTracksCount, totalProgress) {
        Log.d(
          "harmonify",
          "totalTracksCount: $totalTracksCount, downloadingTracksCount: $downloadingTracksCount, totalProgress: $totalProgress"
        )
      }

      DownloadsIconButton(
        totalTracksCount = totalTracksCount,
        downloadingTracksCount = downloadingTracksCount,
        totalProgress = totalProgress,
        onClick = onDownloadsIconButtonClicked
      )
    } else {
      SearchIconButton()
      Log.d("harmonify", "not library route")

    }
  }

  @Composable
  @OptIn(ExperimentalMaterial3Api::class)
  fun TopBar(
    totalTracksCount: Int,
    downloadingTracksCount: Int,
    totalProgress: Float,
    onDownloadsIconButtonClicked: () -> Unit
  ) =
    TopAppBar(
      title = { Text(title, color = MaterialTheme.colorScheme.onPrimary) },
      actions = {
        TopBarActions(
          totalTracksCount,
          downloadingTracksCount,
          totalProgress,
          onDownloadsIconButtonClicked,
        )
      },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
      )
    )

  companion object {
    val DefaultRoute = EXPLORE.route

    fun findByRoute(route: String): BottomNavigationRoute =
      BottomNavigationRoute.entries.first { it.route == route }
  }
}