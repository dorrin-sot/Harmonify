package com.dorrin.harmonify.model

data class Chart(
  val albums: DataList<Album>,
  val artists: DataList<Artist>,
  val tracks: DataList<Track>
)