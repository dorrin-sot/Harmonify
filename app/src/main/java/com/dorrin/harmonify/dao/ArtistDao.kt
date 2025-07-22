package com.dorrin.harmonify.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dorrin.harmonify.model.Artist

@Dao
interface ArtistDao {
  @Query("SELECT * FROM artist")
  fun getAllLiked(): LiveData<List<Artist>>

  @Query("SELECT * FROM artist WHERE id = :id LIMIT 1")
  fun findLiked(id: Long): Artist?

  @Insert
  fun addLiked(vararg artists: Artist)

  @Delete
  fun removeLiked(artist: Artist)

  fun isLiked(id: Long): Boolean = findLiked(id) != null

  fun toggleLiked(artist: Artist) {
    val id = artist.id

    if (isLiked(id)) removeLiked(artist)
    else addLiked(artist)
  }
}
