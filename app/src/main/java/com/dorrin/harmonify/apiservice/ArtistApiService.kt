package com.dorrin.harmonify.apiservice

import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.model.DataList
import com.dorrin.harmonify.model.Track
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistApiService {
  @GET("/artist/{id}")
  suspend fun getArtist(@Path("id") id: Long): Artist

  @GET("/artist/{id}/top")
  suspend fun getArtistTopTracks(@Path("id") id: Long): DataList<Track>

  @GET("/artist/{id}/albums")
  suspend fun getArtistAlbums(@Path("id") id: Long): DataList<Album>
}