package com.dorrin.harmonify.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import com.dorrin.harmonify.view.bottomsheet.ArtistBottomSheet
import com.dorrin.harmonify.view.search.MusicSearchbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseView() {
  val navController = rememberNavController()

  Scaffold(
    topBar = BottomNavigationRoute.findByRoute(
      navController.currentDestination?.route
        ?: BottomNavigationRoute.DefaultRoute
    ).topBar,
    bottomBar = {
      BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary
      ) {
        val currentRoute = navController.currentDestination?.route
          ?: BottomNavigationRoute.DefaultRoute

        BottomNavigationRoute.entries.forEach {
          val selected = currentRoute == it.route
          BottomNavigationItem(
            selected = selected,
            onClick = { navController.navigate(it.route) },
            icon = {
              Icon(
                imageVector = it.icon,
                contentDescription = it.title
              )
            },
            enabled = !selected,
            label = { Text(it.title, style = HarmonifyTypography.bodySmall) },
            selectedContentColor = MaterialTheme.colorScheme.onPrimary
          )
        }
      }
    },
    modifier = Modifier
      .fillMaxSize()
  ) { innerPadding ->
    Box {
      NavHost(
        navController,
        startDestination = BottomNavigationRoute.DefaultRoute,
        modifier = Modifier.padding(innerPadding)
      ) {
        BottomNavigationRoute.entries.forEach { bnr ->
          composable(bnr.route, content = bnr.content)
        }
      }

      MusicSearchbar(
        modifier = Modifier
          .align(Alignment.TopCenter)
          .padding(innerPadding)
      )

      ArtistBottomSheet()
    }

  }
}
