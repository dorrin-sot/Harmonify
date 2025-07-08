package com.dorrin.harmonify.view.section

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
internal fun <T> CardSectionView(
  title: String,
  items: List<T>,
  thumbnailGetter: (item: T) -> String,
  titleGetter: (item: T) -> String,
  trackGetter: (item: T) -> Track?,
  modifier: Modifier,
) {
  BaseSectionView(title, modifier) {
    Row(
      modifier = Modifier
        .horizontalScroll(rememberScrollState())
    ) {
      items.forEach {
        ElevatedCard(
          onClick = { TODO() },
          modifier = Modifier
            .padding(horizontal = 5.dp)
        ) {
          Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.requiredWidth(150.dp)
          ) {
            GlideImage(
              model = thumbnailGetter(it),
              contentDescription = "Album: ${titleGetter(it)}",
              contentScale = ContentScale.FillWidth,
            )

            Row {
              Text(
                titleGetter(it),
                overflow = TextOverflow.Ellipsis,
                style = HarmonifyTypography.bodyLarge,
                maxLines = 2,
                modifier = Modifier.padding(5.dp)
              )

              trackGetter(it)?.let { track ->
                PlayerIconButton(track = track)
              }
            }
          }
        }
      }
    }
  }
}