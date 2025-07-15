package com.dorrin.harmonify.view.settings.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StickyHeader(title: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(10.dp),
    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
  ) {
    HorizontalDivider(
      color = MaterialTheme.colorScheme.secondary,
      modifier = Modifier.weight(1f)
    )
    Text(
      title,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.secondary,
    )
    HorizontalDivider(
      color = MaterialTheme.colorScheme.secondary,
      modifier = Modifier.weight(1f)
    )
  }
}
