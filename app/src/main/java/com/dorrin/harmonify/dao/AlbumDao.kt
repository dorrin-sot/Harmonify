package com.dorrin.harmonify.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dorrin.harmonify.model.Album

@Dao
interface AlbumDao {
  @Query("SELECT * FROM album")
  fun getAllLiked(): LiveData<List<Album>>

  @Query("SELECT * FROM album WHERE id = :id LIMIT 1")
  fun findLiked(id: Long): Album?

  @Insert
  fun addLiked(vararg albums: Album)

  @Delete
  fun removeLiked(album: Album)

  fun isLiked(id: Long): Boolean = findLiked(id) != null

  fun toggleLiked(album: Album) {
    val id = album.id

    if (isLiked(id)) removeLiked(album)
    else addLiked(album)
  }
}
