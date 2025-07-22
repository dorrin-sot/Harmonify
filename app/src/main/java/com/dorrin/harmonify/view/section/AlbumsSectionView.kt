package com.dorrin.harmonify.view.section

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.viewmodel.BottomSheetType.ALBUM_BOTTOM_SHEET
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel

@Composable
internal fun AlbumsSectionView(
  albums: List<Album>,
  modifier: Modifier = Modifier,
  bottomSheetViewModel: BottomSheetViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
) {
  CardSectionView(
    title = "Albums",
    items = albums,
    thumbnailGetter = { it.coverMedium },
    titleGetter = { it.title },
    artistGetter = { it.artist?.name ?: "" },
    onClick = { bottomSheetViewModel.showBottomSheet(ALBUM_BOTTOM_SHEET, it) },
    modifier = modifier,
  )
}