package com.dorrin.harmonify.view.search

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.dorrin.harmonify.viewmodel.SearchViewModel

@Composable
fun MusicSearchbar(modifier: Modifier = Modifier) {
  val viewModel = hiltViewModel<SearchViewModel>(LocalActivity.current as ComponentActivity)

  val searchOn by viewModel.searchOn.observeAsState(false)
  val query by viewModel.query.observeAsState("")

  AnimatedVisibility(
    visible = searchOn,
    enter = fadeIn() + slideInVertically(),
    exit = fadeOut() + slideOutVertically(),
    modifier = modifier
  ) {
    OutlinedTextField(
      value = query,
      onValueChange = { viewModel.search(it) },
      maxLines = 1,
      shape = RoundedCornerShape(100),
      placeholder = { Text("Search music...") },
      leadingIcon = { Icon(Icons.Default.Search, "Search") },
      trailingIcon = {
        IconButton(onClick = { viewModel.search("") }) {
          Icon(Icons.Default.Close, "Clear/Close Search")
        }
      },
      colors = OutlinedTextFieldDefaults.colors().copy(
        unfocusedContainerColor = Color.White.copy(alpha = .9f),
        focusedContainerColor = Color.White,
      ),
      modifier = Modifier
        .zIndex(10f)
        .padding(5.dp)
        .wrapContentHeight()
        .fillMaxWidth(),
    )
  }
}