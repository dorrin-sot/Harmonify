package com.dorrin.harmonify.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dorrin.harmonify.entities.ArtistLike
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ArtistDao : LikeableDao<ArtistLike> {
  @Query("SELECT * FROM artist_likes")
  abstract override fun getAll(): Flow<List<ArtistLike>>

  @Query("SELECT * FROM artist_likes WHERE artist_id = :id LIMIT 1")
  abstract override fun find(id: Long): ArtistLike?

  @Insert
  abstract override fun add(artist: ArtistLike)

  @Delete
  abstract override fun remove(artist: ArtistLike)

  @Update
  abstract override fun update(artist: ArtistLike)
}
