package com.dorrin.harmonify.model

data class Artist(
  val id: Long,
  val name: String,

  val picture: String,
  val pictureSmall: String,
  val pictureMedium: String,
  val pictureBig: String,
  val pictureXl: String,

  val albums: List<Album>?,
  val topTracks: List<Track>?,
)
