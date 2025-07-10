package com.dorrin.harmonify.view

import androidx.compose.animation.AnimatedContentScope
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
import androidx.navigation.NavBackStackEntry
import com.dorrin.harmonify.extension.capitalize
import com.dorrin.harmonify.view.player.PlayerView

enum class BottomNavigationRoute(
  val icon: ImageVector,
  val content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
  SETTINGS(icon = Icons.Default.Settings, content = { TODO() }),
  EXPLORE(icon = Icons.Default.Star, content = { ExploreView() }),
  LIBRARY(icon = Icons.Default.LibraryMusic, content = { TODO() }),
  PLAYER(icon = Icons.Default.PlayArrow, content = { PlayerView() });

  internal val route: String
    get() = "/${name.lowercase()}"

  internal val title: String
    get() = name.replace("_", " ").capitalize()

  private val topBarActions: @Composable RowScope.() -> Unit
    get() = {
//      TODO()
    }

  @OptIn(ExperimentalMaterial3Api::class)
  val topBar: @Composable () -> Unit
    get() = {
      TopAppBar(
        title = { Text(title, color = MaterialTheme.colorScheme.onPrimary) },
        actions = topBarActions,
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primary
        )
      )
    }

  companion object {
    val DefaultRoute = EXPLORE.route

    fun findByRoute(route: String): BottomNavigationRoute =
      BottomNavigationRoute.entries.first { it.route == route }
  }
}