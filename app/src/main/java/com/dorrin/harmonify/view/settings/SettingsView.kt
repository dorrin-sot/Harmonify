package com.dorrin.harmonify.view.settings

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.map
import com.dorrin.harmonify.view.bottomsheet.DurationEditorBottomSheetExtra
import com.dorrin.harmonify.view.settings.item.StickyHeader
import com.dorrin.harmonify.view.settings.item.SwitchItem
import com.dorrin.harmonify.view.settings.item.ValueItem
import com.dorrin.harmonify.view.settings.player.PlayerPreferences.Companion.DEFAULT_AUTO_SLEEP_DURATION_SEC
import com.dorrin.harmonify.view.settings.player.PlayerPreferences.Companion.DEFAULT_AUTO_SLEEP_ENABLED
import com.dorrin.harmonify.viewmodel.BottomSheetType
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel
import com.dorrin.harmonify.viewmodel.SettingsViewModel
import kotlin.time.Duration.Companion.seconds

@Composable
fun SettingsView(modifier: Modifier = Modifier) {
  val viewModel = hiltViewModel<SettingsViewModel>()
  val bottomSheetViewModel =
    hiltViewModel<BottomSheetViewModel>(LocalActivity.current as ComponentActivity)

  LazyColumn(
    verticalArrangement = Arrangement.spacedBy(5.dp),
    modifier = modifier,
  ) {
    stickyHeader { StickyHeader("Player") }

    item {
      val enabled = viewModel.autoSleepEnabled.observeAsState(DEFAULT_AUTO_SLEEP_ENABLED)

      SwitchItem(
        checked = enabled.value,
        toggle = { viewModel.setAutoSleepEnabled(!enabled.value) },
        title = "Auto-Sleep Timer",
        subtitle = "Set an auto-sleep timer to stop music when \"Do not disturb is on\" for \"bedtime\"",
        leadingIcon = Icons.Default.Bedtime,
      )
    }

    item {
      val enabled = viewModel.autoSleepEnabled.observeAsState(DEFAULT_AUTO_SLEEP_ENABLED)
      val duration = viewModel.autoSleepDurationSec
        .map { it.seconds }
        .observeAsState(DEFAULT_AUTO_SLEEP_DURATION_SEC.seconds)

      ValueItem(
        value = duration.value,
        onClick = {
          bottomSheetViewModel.showBottomSheet(
            BottomSheetType.DURATION_EDITOR_BOTTOM_SHEET,
            DurationEditorBottomSheetExtra(
              initialDuration = duration.value,
              onSubmit = { viewModel.setAutoSleepDurationSec(it.inWholeSeconds) },
            )
          )
        },
        title = "Auto-Sleep Timer Duration",
        subtitle = "Set an auto-sleep timer to stop music automatically",
        leadingIcon = Icons.Default.Timer,
        trailingIcon = Icons.Default.MoreTime,
        enabled = enabled.value,
        showValue = duration.value.toString() != "0s"
      )
    }

//    stickyHeader { StickyHeader("Personal Data") }
//
//    item {
//      TaskItem(
//        onClick = { viewModel.resetToDefaults() },
//        title = "Reset to default",
//        leadingIcon = Icons.Default.Replay
//      )
//    }
  }
}
