package com.dorrin.harmonify.model

import com.dorrin.harmonify.model.DataList.Companion.emptyDataList

data class Result(
  var albums: DataList<Album>,
  var artists: DataList<Artist>,
  var tracks: DataList<Track>
) {
  companion object {
    fun emptyResult(): Result =
      Result(emptyDataList<Album>(), emptyDataList<Artist>(), emptyDataList<Track>())
  }
}

