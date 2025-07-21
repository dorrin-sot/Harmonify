package com.dorrin.harmonify.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import com.dorrin.harmonify.view.BottomNavigationRoute.Companion.DefaultRoute
import com.dorrin.harmonify.view.bottomsheet.AlbumBottomSheet
import com.dorrin.harmonify.view.bottomsheet.ArtistBottomSheet
import com.dorrin.harmonify.view.bottomsheet.DurationEditorBottomSheet
import com.dorrin.harmonify.view.search.MusicSearchbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseView() {
  var currentRoute by rememberSaveable { mutableStateOf(DefaultRoute) }
  val currentRouteItem by remember { derivedStateOf { BottomNavigationRoute.findByRoute(currentRoute) } }

  Scaffold(
    topBar = currentRouteItem.topBar,
    bottomBar = {
      BottomAppBar(containerColor = MaterialTheme.colorScheme.primary) {
        BottomNavigationRoute.entries.forEach {
          val selected = currentRoute == it.route
          BottomNavigationItem(
            selected = selected,
            enabled = !selected,
            onClick = { currentRoute = it.route },
            icon = {
              Icon(
                imageVector = it.icon,
                contentDescription = it.title
              )
            },
            label = { Text(it.title, style = HarmonifyTypography.bodySmall) },
            selectedContentColor = MaterialTheme.colorScheme.onPrimary,
          )
        }
      }
    },
    modifier = Modifier
      .fillMaxSize()
  ) { innerPadding ->
    Box {
      Box(modifier = Modifier.padding(innerPadding)) {
        currentRouteItem.content()
      }

      MusicSearchbar(
        modifier = Modifier
          .align(Alignment.TopCenter)
          .padding(innerPadding)
      )

      ArtistBottomSheet()
      AlbumBottomSheet()
      DurationEditorBottomSheet()
    }
  }
}
