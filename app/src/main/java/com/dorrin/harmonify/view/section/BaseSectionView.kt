package com.dorrin.harmonify.view.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dorrin.harmonify.ui.theme.HarmonifyTypography

@Composable
internal fun BaseSectionView(
  title: String,
  modifier: Modifier,
  collectionViewBuilder: @Composable () -> Unit,
) {
  Column(
    modifier = Modifier
      .padding(10.dp)
      .then(modifier)
  ) {
    Text(
      title,
      modifier = Modifier.padding(vertical = 10.dp),
      style = HarmonifyTypography.titleLarge
    )
    collectionViewBuilder()
  }
}