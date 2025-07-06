package com.dorrin.harmonify.view.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import com.dorrin.harmonify.view.PlayerIconButton

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun <T> GridSectionView(
  title: String,
  items: List<T>,
  thumbnailGetter: (item: T) -> String,
  titleGetter: (item: T) -> String,
  trackGetter: (item: T) -> Track,
  onEnqueue: (item: T) -> Unit,
  modifier: Modifier,
) {
  BaseSectionView(title, modifier) {
    LazyVerticalGrid (
      columns = GridCells.Fixed(2),
      modifier = Modifier.fillMaxHeight(),
      userScrollEnabled = false
    ) {
      items(items) {
        ElevatedCard(
          onClick = { TODO() },
          modifier = Modifier.padding(5.dp),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
              .requiredHeight(50.dp)
              .fillMaxWidth()
          ) {
            GlideImage(
              model = thumbnailGetter(it),
              contentDescription = titleGetter(it),
              contentScale = ContentScale.FillHeight,
            )

            Text(
              titleGetter(it),
              overflow = TextOverflow.Ellipsis,
              style = HarmonifyTypography.bodyMedium,
              maxLines = 1,
              modifier = Modifier.padding(5.dp),
            )
            PlayerIconButton(
              trackGetter(it),
              onEnqueue = { onEnqueue(it) },
            )
          }
        }
      }
    }
  }
}