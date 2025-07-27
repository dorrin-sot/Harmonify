package com.dorrin.harmonify.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dorrin.harmonify.model.Track

@Entity(TrackLike.TABLE_NAME, primaryKeys = ["track_id"])
data class TrackLike(
  @Embedded("track_") val track: Track,
  val downloadPath: String? = null
) {
  companion object {
    const val TABLE_NAME = "track_likes"
  }
}
