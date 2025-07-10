package com.dorrin.harmonify.view.section

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dorrin.harmonify.ui.theme.HarmonifyTypography

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun <T> ProfileSectionView(
  title: String,
  items: List<T>,
  thumbnailGetter: (item: T) -> String,
  titleGetter: (item: T) -> String,
  onClick: (item: T) -> Unit,
  modifier: Modifier,
) {

  BaseSectionView(title, modifier) {
    Row(
      modifier = Modifier
        .horizontalScroll(rememberScrollState())
    ) {
      items.forEach {
        Surface(
          modifier = Modifier.padding(horizontal = 5.dp),
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.requiredWidth(75.dp)
          ) {
            GlideImage(
              model = thumbnailGetter(it),
              contentDescription = titleGetter(it),
              contentScale = ContentScale.FillWidth,
              modifier = Modifier
                .clip(CircleShape)
                .shadow(2.dp, CircleShape)
                .clickable(onClick = { onClick(it) }),
            )

            Text(
              titleGetter(it),
              overflow = TextOverflow.Ellipsis,
              style = HarmonifyTypography.bodySmall,
              maxLines = 1,
              modifier = Modifier.padding(5.dp),
              textAlign = TextAlign.Center
            )
          }
        }
      }
    }
  }
}