package com.dorrin.harmonify.view.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dorrin.harmonify.ui.theme.HarmonifyTypography

@Composable
internal fun <T> SectionView(
  title: String,
  items: List<T>,
  modifier: Modifier,
  itemContent: @Composable LazyItemScope.(item: T) -> Unit,
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
    LazyRow {
      items(items, itemContent = {
        itemContent(it)
      })
    }
  }
}