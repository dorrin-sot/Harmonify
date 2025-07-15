package com.dorrin.harmonify.view.bottomsheet

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.extension.toComponentsHHMMSS
import com.dorrin.harmonify.view.WheelDurationPicker
import com.dorrin.harmonify.viewmodel.BottomSheetType
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Composable
fun DurationEditorBottomSheet(
  bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
) {
  var initialDuration: Triple<Int, Int, Int>? = null
  var onSubmit: ((Duration) -> Unit)? = null

  ConditionalBottomSheet(
    type = BottomSheetType.DURATION_EDITOR_BOTTOM_SHEET,
    onExtraGet = {
      val extra = it as? DurationEditorBottomSheetExtra
      extra ?: return@ConditionalBottomSheet

      initialDuration = extra.initialDuration.toComponentsHHMMSS()
      onSubmit = extra.onSubmit
    },
    bottomSheetViewModel,
  ) {
    Column(
      Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
      verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
    ) {
      var durationHH by remember { mutableIntStateOf(initialDuration?.first ?: 0) }
      var durationMM by remember { mutableIntStateOf(initialDuration?.second ?: 0) }
      var durationSS by remember { mutableIntStateOf(initialDuration?.third ?: 0) }

      val duration by remember { derivedStateOf { durationHH.hours + durationMM.minutes + durationSS.seconds } }

      WheelDurationPicker(
        duration = duration,
        onHourChanged = { durationHH = it },
        onMinuteChanged = { durationMM = it },
        onSecondChanged = { durationSS = it },
        modifier = Modifier.padding(horizontal = 40.dp, vertical = 10.dp)
      )

      ElevatedButton(
        onClick = {
          onSubmit?.invoke(duration)
          bottomSheetViewModel.hideBottomSheet()
        },
        modifier = Modifier.fillMaxWidth()
      ) { Text("Submit") }

      TextButton(
        onClick = { bottomSheetViewModel.hideBottomSheet() },
        modifier = Modifier.fillMaxWidth()
      ) { Text("Discard Changes") }
    }
  }
}

data class DurationEditorBottomSheetExtra(
  val initialDuration: Duration,
  val onSubmit: (Duration) -> Unit = {},
)

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun DurationEditorBottomSheetPreview() {
  val bottomSheetViewModel = BottomSheetViewModel()

  LaunchedEffect(Unit) {
    bottomSheetViewModel.showBottomSheet(
      BottomSheetType.DURATION_EDITOR_BOTTOM_SHEET,
      DurationEditorBottomSheetExtra(30.minutes)
    )
  }

  Box { Text("Hello World") }
  DurationEditorBottomSheet(bottomSheetViewModel)
}
