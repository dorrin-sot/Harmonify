package com.dorrin.harmonify.view.settings.item

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
internal fun SwitchItem(
  checked: Boolean,
  toggle: () -> Unit,
  title: String,
  subtitle: String? = null,
  leadingIcon: ImageVector? = null
) {

  ListItem(
    headlineContent = { Text(title) },
    supportingContent = { subtitle?.let { Text(it) } },
    leadingContent = { leadingIcon?.let { Icon(it, it.name) } },
    trailingContent = {
      Switch(
        checked = checked,
        onCheckedChange = { toggle() }
      )
    },
    modifier = Modifier.clickable(onClick = { toggle() })
  )
}