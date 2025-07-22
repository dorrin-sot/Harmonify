package com.dorrin.harmonify.view.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import com.dorrin.harmonify.viewmodel.PlayerViewModel
import java.util.Date

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun <T> CardSectionView(
  title: String,
  items: List<T>,
  thumbnailGetter: (item: T) -> String,
  titleGetter: (item: T) -> String,
  artistGetter: (item: T) -> String,
  onClick: (item: T) -> Unit,
  modifier: Modifier,
) {
  BaseSectionView(title, modifier) {
    Row(
      modifier = Modifier
        .horizontalScroll(rememberScrollState())
        .height(intrinsicSize = IntrinsicSize.Max)
    ) {
      items.forEach {
        ElevatedCard(
          onClick = { onClick(it) },
          modifier = Modifier
            .padding(horizontal = 5.dp)
        ) {
          Column(
            modifier = Modifier.requiredWidth(150.dp)
          ) {
            GlideImage(
              model = thumbnailGetter(it),
              contentDescription = "Album: ${titleGetter(it)}",
              contentScale = ContentScale.FillWidth,
              modifier = Modifier.aspectRatio(1f)
            )

            Column(
              verticalArrangement = Arrangement.spacedBy(5.dp),
              modifier = Modifier.padding(7.dp),
            ) {
              Text(
                titleGetter(it),
                overflow = TextOverflow.Ellipsis,
                style = HarmonifyTypography.titleMedium,
                maxLines = 1,
              )
              Text(
                artistGetter(it),
                overflow = TextOverflow.Ellipsis,
                style = HarmonifyTypography.bodyMedium,
                maxLines = 1,
              )
            }
          }
        }
      }
    }
  }
}