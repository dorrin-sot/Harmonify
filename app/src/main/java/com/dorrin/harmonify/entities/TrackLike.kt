package com.dorrin.harmonify.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.work.WorkInfo
import com.dorrin.harmonify.model.Track

@Entity(TrackLike.TABLE_NAME, primaryKeys = ["track_id"])
data class TrackLike(
  @Embedded("track_") val track: Track,
  val downloadPath: String? = null,
  val state: WorkInfo.State? = null,
  val progress: Float? = null,
  val bytesDownloaded: Long? = null,
  val totalBytes: Long? = null,
) {
  companion object {
    const val TABLE_NAME = "track_likes"
  }
}
