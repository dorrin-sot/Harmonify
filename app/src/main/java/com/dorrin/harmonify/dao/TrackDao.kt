package com.dorrin.harmonify.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dorrin.harmonify.model.Track

@Dao
interface TrackDao {
  @Query("SELECT * FROM track")
  fun getAllLiked(): LiveData<List<Track>>

  @Query("SELECT * FROM track WHERE id = :id LIMIT 1")
  fun findLiked(id: Long): Track?

  @Insert
  fun addLiked(vararg tracks: Track)

  @Delete
  fun removeLiked(track: Track)

  fun isLiked(id: Long): Boolean = findLiked(id) != null

  fun toggleLiked(track: Track) {
    val id = track.id

    if (isLiked(id)) removeLiked(track)
    else addLiked(track)
  }
}