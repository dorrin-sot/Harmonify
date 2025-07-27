package com.dorrin.harmonify.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dorrin.harmonify.entities.TrackLike
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrackDao : LikeableDao<TrackLike> {
  @Query("SELECT * FROM track_likes")
  abstract override fun getAll(): Flow<List<TrackLike>>

  @Query("SELECT * FROM track_likes WHERE track_id = :id LIMIT 1")
  abstract override fun find(id: Long): TrackLike?

  @Insert
  abstract override fun add(track: TrackLike)

  @Delete
  abstract override fun remove(track: TrackLike)

  @Update
  abstract override fun update(track: TrackLike)
}