package com.dorrin.harmonify.conversion

import androidx.room.TypeConverter
import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.model.DataList
import com.dorrin.harmonify.model.Genre
import com.dorrin.harmonify.model.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class DBConverters {
  private val gson = Gson()

  @TypeConverter
  fun fromArtist(artist: Artist?): String? = gson.toJson(artist)

  @TypeConverter
  fun toArtist(json: String?): Artist? = json?.let { gson.fromJson(it, Artist::class.java) }

  @TypeConverter
  fun fromAlbum(album: Album?): String? = gson.toJson(album)

  @TypeConverter
  fun toAlbum(json: String?): Album? = json?.let { gson.fromJson(it, Album::class.java) }

  @TypeConverter
  fun fromGenreDataList(list: DataList<Genre>?): String? = gson.toJson(list)

  @TypeConverter
  fun toGenreDataList(json: String?): DataList<Genre>? =
    json?.let {
      val type = object : TypeToken<DataList<Genre>>() {}.type
      gson.fromJson(it, type)
    }

  @TypeConverter
  fun fromTrackDataList(list: DataList<Track>?): String? = gson.toJson(list)

  @TypeConverter
  fun toTrackDataList(json: String?): DataList<Track>? =
    json?.let {
      val type = object : TypeToken<DataList<Track>>() {}.type
      gson.fromJson(it, type)
    }

  @TypeConverter
  fun fromAlbumDataList(list: DataList<Album>?): String? = gson.toJson(list)

  @TypeConverter
  fun toAlbumDataList(json: String?): DataList<Album>? =
    json?.let {
      val type = object : TypeToken<DataList<Album>>() {}.type
      gson.fromJson(it, type)
    }

  @TypeConverter
  fun fromDate(date: Date?): Long? = date?.time

  @TypeConverter
  fun toDate(time: Long?): Date? = time?.let { Date(it) }
}