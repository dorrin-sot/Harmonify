package com.dorrin.harmonify.view.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dorrin.harmonify.model.Track
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import com.dorrin.harmonify.view.PlaylistAddRemoveIconButton
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
internal fun <T> GridSectionView(
  title: String,
  items: List<T>,
  thumbnailGetter: (item: T) -> String,
  titleGetter: (item: T) -> String,
  trackGetter: (item: T) -> Track,
  maxItemsInEachRow: Int = 2,
  modifier: Modifier,
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity)
) {
  BaseSectionView(title, modifier) {
    FlowRow(
      maxItemsInEachRow = maxItemsInEachRow,
      modifier = Modifier.wrapContentHeight(),
    ) {
      items.forEachIndexed { idx, it ->
        ElevatedCard(
          onClick = { TODO() },
          modifier = Modifier
            .padding(5.dp)
            .requiredHeight(50.dp)
            .weight(1f),
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
          ) {
            Box {
              GlideImage(
                model = thumbnailGetter(it),
                contentDescription = titleGetter(it),
                contentScale = ContentScale.FillHeight,
              )

              Text(
                "${idx + 1}.",
                style = HarmonifyTypography.bodyMedium
                  .copy(
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    shadow = Shadow(
                      color = Color.Black,
                      offset = Offset(2f, 2f),
                      blurRadius = 4f
                    )
                  ),
                modifier = Modifier
                  .padding(horizontal = 3.dp, vertical = 2.dp)
                  .align(Alignment.TopStart)
              )
            }

            Text(
              titleGetter(it),
              overflow = TextOverflow.Ellipsis,
              style = HarmonifyTypography.bodyMedium,
              maxLines = 1,
              modifier = Modifier.weight(1f),
            )

            PlaylistAddRemoveIconButton(track = trackGetter(it), playerViewModel = playerViewModel)
          }
        }
      }
    }
  }
}