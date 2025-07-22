package com.dorrin.harmonify.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Artist(
  @PrimaryKey var id: Long,
  var name: String,

  var picture: String,
  var pictureSmall: String,
  var pictureMedium: String,
  var pictureBig: String,
  var pictureXl: String,

  @Ignore var albums: List<Album>?,
  @Ignore var topTracks: List<Track>?,
) {
  constructor(
    id: Long,
    name: String,
    picture: String,
    pictureSmall: String,
    pictureMedium: String,
    pictureBig: String,
    pictureXl: String,
  ) : this(id, name, picture, pictureSmall, pictureMedium, pictureBig, pictureXl, null, null)
}
