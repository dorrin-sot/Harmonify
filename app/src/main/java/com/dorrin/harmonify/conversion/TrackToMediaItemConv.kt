package com.dorrin.harmonify.conversion

import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.dorrin.harmonify.model.Track

fun Track.toMediaItem(): MediaItem = MediaItem.Builder()
  .setMediaId(id.toString())
  .setUri(preview)
  .setMediaMetadata(
    MediaMetadata
      .Builder()
      .setTitle(title)
      .setArtist(artist.name)
      .setAlbumTitle(album.title)
      .setGenre(album.genres?.data?.first()?.name ?: "")
      .setArtworkUri(album.coverBig.toUri())
      .setDurationMs((duration * 1000).toLong())
      .setReleaseYear(album.releaseDate?.year)
      .setReleaseMonth(album.releaseDate?.month)
      .setReleaseDay(album.releaseDate?.day)
      .build()
  )
  .setTag(this)
  .build()