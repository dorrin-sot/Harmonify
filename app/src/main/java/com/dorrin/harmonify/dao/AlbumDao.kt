package com.dorrin.harmonify.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.dorrin.harmonify.entities.AlbumLike
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AlbumDao : LikeableDao<AlbumLike> {
  @Query("SELECT * FROM album_likes")
  abstract override fun getAll(): Flow<List<AlbumLike>>

  @Query("SELECT * FROM album_likes WHERE album_id = :albumId LIMIT 1")
  abstract override fun find(albumId: Long): AlbumLike?

  @Insert
  abstract override fun add(album: AlbumLike)

  @Delete
  abstract override fun remove(album: AlbumLike)

  @Update
  abstract override fun update(album: AlbumLike)
}
