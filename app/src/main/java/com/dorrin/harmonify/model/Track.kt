package com.dorrin.harmonify.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Track(
  @PrimaryKey var id: Long,
  var title: String,
  var duration: Int,
  var preview: String,
  var link: String,
  @Embedded("artist_") var artist: Artist,
  @Embedded("album_") var album: Album
)
