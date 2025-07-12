package com.dorrin.harmonify.view.bottomsheet

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dorrin.harmonify.HarmonifyModule
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.provider.MediaControllerProvider
import com.dorrin.harmonify.ui.theme.HarmonifyTypography
import com.dorrin.harmonify.view.section.AlbumsSectionView
import com.dorrin.harmonify.view.section.TracksSectionView
import com.dorrin.harmonify.viewmodel.ArtistViewModel
import com.dorrin.harmonify.viewmodel.BottomSheetType
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel
import com.dorrin.harmonify.viewmodel.ExploreViewModel
import com.dorrin.harmonify.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun ArtistBottomSheet(
  artistViewModel: ArtistViewModel = hiltViewModel(),
  bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
  playerViewModel: PlayerViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
) {
  val artist = artistViewModel.artist.observeAsState()

  ConditionalBottomSheet(
    type = BottomSheetType.ARTIST_BOTTOM_SHEET,
    onExtraGet = { it?.let { artistViewModel.setArtist(it as Artist) } },
    bottomSheetViewModel,
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(5.dp),
      modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
      Box(
        modifier = Modifier
          .wrapContentSize()
          .height(IntrinsicSize.Min),
      ) {
        GlideImage(
          artist.value?.pictureXl ?: artist.value?.pictureBig ?: "",
          contentDescription = artist.value?.name,
          contentScale = ContentScale.FillWidth,
          alignment = Alignment.TopCenter,
          modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .aspectRatio(1.75f)
        )
        Text(
          artist.value?.name ?: "",
          style = HarmonifyTypography.titleLarge
            .copy(
              fontWeight = FontWeight.Bold,
              color = Color.White,
              shadow = Shadow(
                color = Color.Black,
                offset = Offset(4f, 4f),
                blurRadius = 8f
              )
            ),
          modifier = Modifier.padding(15.dp)
        )
      }

      AlbumsSectionView(
        albums = artist.value?.albums ?: emptyList(),
        playerViewModel = playerViewModel,
        modifier = Modifier.wrapContentSize()
      )

      HorizontalDivider()

      TracksSectionView(
        tracks = artist.value?.topTracks ?: emptyList(),
        playerViewModel = playerViewModel,
        modifier = Modifier.wrapContentSize()
      )
    }
  }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun ArtistBottomSheetPreview() {
  val exploreViewModel = ExploreViewModel(
    HarmonifyModule.providesChartService(),
    HarmonifyModule.providesAlbumService()
  )
  val bottomSheetViewModel = BottomSheetViewModel()
  val artistViewModel = ArtistViewModel(
    HarmonifyModule.providesArtistService()
  )
  val playerViewModel = PlayerViewModel(
    MediaControllerProvider(
      HarmonifyModule.providesMediaController(LocalContext.current)
    )
  )

  exploreViewModel.chartArtists
    .observeForever { it ->
      val artist = it.first()
      bottomSheetViewModel.showBottomSheet(BottomSheetType.ARTIST_BOTTOM_SHEET, artist)
    }

  Box { Text("Hello World") }
  ArtistBottomSheet(
    artistViewModel,
    bottomSheetViewModel,
    playerViewModel
  )
}