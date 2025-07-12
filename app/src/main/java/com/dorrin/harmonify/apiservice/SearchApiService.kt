package com.dorrin.harmonify.apiservice

import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.model.DataList
import com.dorrin.harmonify.model.Result
import com.dorrin.harmonify.model.Track
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
  @GET("/search/track")
  suspend fun searchTracks(@Query("q") q: String): DataList<Track>

  @GET("/search/album")
  suspend fun searchAlbums(@Query("q") q: String): DataList<Album>

  @GET("/search/artist")
  suspend fun searchArtists(@Query("q") q: String): DataList<Artist>
}