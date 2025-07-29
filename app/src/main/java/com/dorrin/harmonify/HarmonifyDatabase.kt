package com.dorrin.harmonify

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dorrin.harmonify.conversion.DBConverters
import com.dorrin.harmonify.dao.TrackDao
import com.dorrin.harmonify.entities.TrackLike

@Database(entities = [TrackLike::class], version = 1)
@TypeConverters(DBConverters::class)
abstract class HarmonifyDatabase : RoomDatabase() {
  abstract fun trackDao(): TrackDao
}