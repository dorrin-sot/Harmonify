package com.dorrin.harmonify.view.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.viewmodel.BottomSheetType.ARTIST_BOTTOM_SHEET
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArtistsSectionView(artists: List<Artist>, modifier: Modifier = Modifier) {
  val bottomSheetViewModel = hiltViewModel<BottomSheetViewModel>(
    LocalActivity.current as ComponentActivity
  )
  ProfileSectionView(
    title = "Artists",
    items = artists,
    thumbnailGetter = { it.pictureMedium },
    titleGetter = { it.name },
    onClick = { bottomSheetViewModel.showBottomSheet(ARTIST_BOTTOM_SHEET, it) },
    modifier = modifier
  )
}