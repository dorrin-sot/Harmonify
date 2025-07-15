package com.dorrin.harmonify.view.settings.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dorrin.harmonify.ui.theme.HarmonifyTypography

@Composable
internal fun <T> ValueItem(
  value: T,
  onClick: () -> Unit,
  title: String,
  subtitle: String? = null,
  leadingIcon: ImageVector? = null,
  trailingIcon: ImageVector? = null,
  enabled: Boolean = true,
  showValue: Boolean = true,
) {

  ListItem(
    headlineContent = { Text(title) },
    supportingContent = { subtitle?.let { Text(subtitle) } },
    leadingContent = { leadingIcon?.let { Icon(it, it.name) } },
    trailingContent = {
      Column(
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        trailingIcon?.let { Icon(it, it.name) }

        if (showValue)
          Text(
            value.toString(),
            style = HarmonifyTypography.labelMedium
          )
      }
    },
    modifier = Modifier.clickable(
      onClick = onClick,
      enabled = enabled
    ),
  )
}