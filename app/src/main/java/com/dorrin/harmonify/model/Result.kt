package com.dorrin.harmonify.model

data class Result(
  var albums: DataList<Album>,
  var artists: DataList<Artist>,
  var tracks: DataList<Track>
)

fun emptyResult(): Result =
  Result(emptyDataList<Album>(), emptyDataList<Artist>(), emptyDataList<Track>())