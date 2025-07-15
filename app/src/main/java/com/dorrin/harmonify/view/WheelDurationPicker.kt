package com.dorrin.harmonify.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import com.commandiron.wheel_picker_compose.core.WheelTextPicker
import com.dorrin.harmonify.extension.toComponentsHHMMSS
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration


@Composable
fun WheelDurationPicker(
  duration: Duration = Duration.ZERO,
  onHourChanged: (Int) -> Unit = {},
  onMinuteChanged: (Int) -> Unit = {},
  onSecondChanged: (Int) -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val hours = 0..8
  val minutes = 0..59
  val seconds = 0..59

  val durationTriple by remember { derivedStateOf { duration.toComponentsHHMMSS() } }

  val style = HarmonifyTypography.titleMedium
    .copy(fontWeight = FontWeight.Light)
  val boldStyle = style.copy(fontWeight = FontWeight.SemiBold)

  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
  ) {
    val shape = RoundedCornerShape(10.dp)
    Surface(
      color = MaterialTheme.colorScheme.primaryContainer,
      shape = shape,
      modifier = Modifier
        .height(30.dp)
        .fillMaxWidth()
        .padding(horizontal = 25.dp)
        .border(1.dp, MaterialTheme.colorScheme.onPrimaryContainer, shape)
    ) {}

    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
    ) {
      Spacer(Modifier.weight(2f))
      WheelTextPicker(
        texts = hours.map { "$it" }.toList(),
        startIndex = durationTriple.first,
        rowCount = 3,
        onScrollFinished = { min(hours.last, max(it, hours.first)).also { onHourChanged(it) } },
        style = boldStyle,
        modifier = Modifier.weight(1f),
        selectorProperties = WheelPickerDefaults.selectorProperties(enabled = false)
      )
      Text("h.", style = style)
      Spacer(Modifier.weight(.5f))
      WheelTextPicker(
        texts = minutes.map { "$it" }.toList(),
        startIndex = durationTriple.second,
        rowCount = 3,
        onScrollFinished = {
          min(
            minutes.last,
            max(it, minutes.first)
          ).also { onMinuteChanged(it) }
        },
        style = boldStyle,
        modifier = Modifier.weight(1f),
        selectorProperties = WheelPickerDefaults.selectorProperties(enabled = false)
      )
      Text("m.", style = style)
      Spacer(Modifier.weight(.5f))
      WheelTextPicker(
        texts = seconds.map { "$it" }.toList(),
        startIndex = durationTriple.third,
        rowCount = 3,
        onScrollFinished = {
          min(
            seconds.last,
            max(it, seconds.first)
          ).also { onSecondChanged(it) }
        },
        style = boldStyle,
        modifier = Modifier.weight(1f),
        selectorProperties = WheelPickerDefaults.selectorProperties(enabled = false)
      )
      Text("s.", style = style)
      Spacer(Modifier.weight(2f))
    }
  }
}