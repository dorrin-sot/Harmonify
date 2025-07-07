package com.dorrin.harmonify.view

import android.content.ComponentName
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dorrin.harmonify.service.PlayerService
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import com.dorrin.harmonify.viewmodel.PlayerViewModel
import com.google.common.util.concurrent.MoreExecutors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseView() {
  val playerViewModel = hiltViewModel<PlayerViewModel>()

  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  DisposableEffect (lifecycleOwner) {
    val lifecycleEventObserver = LifecycleEventObserver { _, event ->
      if (event == Lifecycle.Event.ON_CREATE)
        initializeMediaController(context, playerViewModel)
    }

    lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

    onDispose {
      lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
    }
  }

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
    NavHost(
      navController,
      startDestination = BottomNavigationRoute.DefaultRoute,
      modifier = Modifier.padding(innerPadding)
    ) {
      BottomNavigationRoute.entries.forEach { bnr ->
        composable(bnr.route, content = bnr.content)
      }
    }
  }
}

private fun initializeMediaController(context: Context, playerViewModel: PlayerViewModel) {
  println("MainActivity::initializeMediaController")
  val sessionToken = SessionToken(context, ComponentName(context, PlayerService::class.java))
  val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
  controllerFuture.addListener(
    { playerViewModel.setMediaController(controllerFuture.get()) },
    MoreExecutors.directExecutor()
  )
}