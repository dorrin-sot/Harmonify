package com.dorrin.harmonify.view.search

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.viewmodel.SearchViewModel

@Composable
fun SearchIconButton() {
  val viewModel = hiltViewModel<SearchViewModel>(LocalActivity.current as ComponentActivity)

  val searchOn by viewModel.searchOn.observeAsState(false)

  if (searchOn) {
    IconButton(onClick = { viewModel.setSearchOn(true) }) {
      Icon(
        Icons.Default.Search,
        contentDescription = "Search"
      )
    }
  }
}