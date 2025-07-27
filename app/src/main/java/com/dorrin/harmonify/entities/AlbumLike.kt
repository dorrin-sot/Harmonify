package com.dorrin.harmonify.entities

import androidx.room.Embedded
import androidx.room.Entity
import com.dorrin.harmonify.model.Album

@Entity(AlbumLike.TABLE_NAME, primaryKeys = ["album_id"])
data class AlbumLike(
  @Embedded("album_") val album: Album,
  val downloadPath: String? = null,
) {
  companion object {
    const val TABLE_NAME = "album_likes"
  }
}