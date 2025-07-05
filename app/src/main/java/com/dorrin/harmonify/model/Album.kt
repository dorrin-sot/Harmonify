package com.dorrin.harmonify.model

import java.util.Date

data class Album(
  val id: Long,
  val title: String,

  val cover: String,
  val coverSmall: String,
  val coverMedium: String,
  val coverBig: String,
  val coverXl: String,

  val genres: DataList<Genre>?,
  val label: String?,
  val releaseDate: Date?,
  val artist: Artist?,
  val tracks: DataList<Track>?
)
