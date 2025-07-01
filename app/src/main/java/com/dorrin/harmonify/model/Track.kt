package com.dorrin.harmonify.model

import kotlin.time.Duration

data class Track(
  val id: String,
  val title: String,
  val duration: Duration,
  val preview: String,
  val artist: Artist,
  val album: Album
)
