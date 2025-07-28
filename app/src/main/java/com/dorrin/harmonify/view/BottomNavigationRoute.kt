package com.dorrin.harmonify.view

import androidx.compose.foundation.layout.RowScope
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

  private val topBarActions: @Composable RowScope.() -> Unit
    get() = {
      if (route == LIBRARY.route) {
        DownloadsIconButton()
      } else {
        SearchIconButton()
      }
    }

  @OptIn(ExperimentalMaterial3Api::class)
  val topBar: @Composable () -> Unit
    get() = {
      TopAppBar(
        title = { Text(title, color = MaterialTheme.colorScheme.onPrimary) },
        actions = topBarActions,
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary,
          actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
          titleContentColor = MaterialTheme.colorScheme.onPrimary,
        )
      )
    }

  companion object {
    val DefaultRoute = EXPLORE.route

    fun findByRoute(route: String): BottomNavigationRoute =
      BottomNavigationRoute.entries.first { it.route == route }
  }
}