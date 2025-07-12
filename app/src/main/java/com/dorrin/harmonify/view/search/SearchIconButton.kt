package com.dorrin.harmonify.view.search

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.viewmodel.SearchViewModel

@Composable
fun SearchIconButton(modifier: Modifier = Modifier) {
  val viewModel = hiltViewModel<SearchViewModel>(LocalActivity.current as ComponentActivity)

  val searchOn = viewModel.searchOn.observeAsState()

  if (searchOn.value != true) {
    IconButton(onClick = { viewModel.setSearchOn(true) }) {
      Icon(
        Icons.Default.Search,
        contentDescription = "Search"
      )
    }
  }
}