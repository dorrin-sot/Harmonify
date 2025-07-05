package com.dorrin.harmonify.model

data class Track(
  val id: Long,
  val title: String,
  val duration: Int,
  val preview: String,
  val artist: Artist,
  val album: Album
)
