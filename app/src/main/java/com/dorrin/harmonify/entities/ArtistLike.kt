package com.dorrin.harmonify.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dorrin.harmonify.model.Artist

@Entity(ArtistLike.TABLE_NAME)
data class ArtistLike(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @Embedded("artist_") val artist: Artist,
  val downloadPath: String? = null
) {
  companion object {
    const val TABLE_NAME = "artist_likes"
  }
}
