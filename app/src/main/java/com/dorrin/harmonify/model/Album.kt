package com.dorrin.harmonify.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Album(
  @PrimaryKey var id: Long,
  var title: String,

  var cover: String,
  var coverSmall: String,
  var coverMedium: String,
  var coverBig: String,
  var coverXl: String,

  @Ignore var genres: DataList<Genre>?,
  @Ignore var label: String?,
  @Ignore var releaseDate: Date?,
  @Ignore var artist: Artist?,
  @Ignore var tracks: DataList<Track>?,
) {
  constructor(
    id: Long,
    title: String,
    cover: String,
    coverSmall: String,
    coverMedium: String,
    coverBig: String,
    coverXl: String,
  ) :
      this(
        id,
        title,
        cover,
        coverSmall,
        coverMedium,
        coverBig,
        coverXl,
        null,
        null,
        null,
        null,
        null,
      )
}
