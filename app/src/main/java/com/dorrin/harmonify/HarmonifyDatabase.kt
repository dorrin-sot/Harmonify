package com.dorrin.harmonify

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dorrin.harmonify.conversion.DBConverters
import com.dorrin.harmonify.dao.AlbumDao
import com.dorrin.harmonify.dao.ArtistDao
import com.dorrin.harmonify.dao.TrackDao
import com.dorrin.harmonify.entities.AlbumLike
import com.dorrin.harmonify.entities.ArtistLike
import com.dorrin.harmonify.entities.TrackLike

@Database(entities = [AlbumLike::class, ArtistLike::class, TrackLike::class], version = 3)
@TypeConverters(DBConverters::class)
abstract class HarmonifyDatabase : RoomDatabase() {
  abstract fun albumDao(): AlbumDao
  abstract fun artistDao(): ArtistDao
  abstract fun trackDao(): TrackDao
}