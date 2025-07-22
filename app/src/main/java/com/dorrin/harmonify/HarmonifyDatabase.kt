package com.dorrin.harmonify

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dorrin.harmonify.dao.AlbumDao
import com.dorrin.harmonify.dao.ArtistDao
import com.dorrin.harmonify.dao.TrackDao
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.model.Track

@Database(entities = [Album::class, Artist::class, Track::class], version = 1)
abstract class HarmonifyDatabase : RoomDatabase() {
  abstract fun albumDao(): AlbumDao
  abstract fun artistDao(): ArtistDao
  abstract fun trackDao(): TrackDao
}