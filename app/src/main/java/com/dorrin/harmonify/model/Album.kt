package com.dorrin.harmonify.model

import java.util.Date

data class Album(
  val id: String,
  val title: String,
  val cover: SizedPicture,
  val genres: List<Genre>,
  val label: String,
  val releaseDate: Date,
  val artist: Artist,
  val tracks: List<Track>
)
