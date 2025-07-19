package com.dorrin.harmonify.view.bottomsheet

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.map
import com.dorrin.harmonify.viewmodel.BottomSheetType
import com.dorrin.harmonify.viewmodel.BottomSheetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConditionalBottomSheet(
  type: BottomSheetType,
  onExtraGet: (extra: Any?) -> Unit,
  viewModel: BottomSheetViewModel = hiltViewModel(LocalActivity.current as ComponentActivity),
  content: @Composable ColumnScope.() -> Unit,
) {
  val shouldShow by viewModel.backStack.map { it.contains(type) }.observeAsState(false)

  if (shouldShow) {
    val index by viewModel.backStack.map { it.indexOf(type) }.observeAsState(0)
    val extra by viewModel.extras.map { it.getOrNull(index) }.observeAsState()

    LaunchedEffect(extra) { onExtraGet(extra) }

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
      onDismissRequest = { viewModel.hideBottomSheet() },
      sheetState = sheetState,
      dragHandle = {},
      content = content
    )
  }
}