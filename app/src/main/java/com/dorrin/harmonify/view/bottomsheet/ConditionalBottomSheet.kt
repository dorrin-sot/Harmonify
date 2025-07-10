package com.dorrin.harmonify.view.bottomsheet

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.map
import com.dorrin.harmonify.viewmodel.ModalBottomSheetType
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConditionalBottomSheet(
  type: ModalBottomSheetType,
  onExtraGet: (extra: Any?) -> Unit,
  viewModel: BottomSheetViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
  content: @Composable ColumnScope.() -> Unit,
) {
  val shouldShow = viewModel.backStack.map { it.contains(type) }.observeAsState()

  if (shouldShow.value == true) {
    val index = viewModel.backStack.map { it.indexOf(type) }.observeAsState()
    val extra = viewModel.extras.map { it.getOrNull(index.value!!) }.observeAsState()

    LaunchedEffect(extra) { onExtraGet(extra.value) }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
      onDismissRequest = { viewModel.hideBottomSheet() },
      sheetState = sheetState,
      content = content
    )
  }
}