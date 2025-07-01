package com.dorrin.harmonify.service

import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Artist
import com.dorrin.harmonify.model.Track
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistService {
  @GET("/artist/{id}")
  suspend fun getArtist(@Path("id") id: String): Call<Artist>

  @GET("/artist/{id}/top")
  suspend fun getArtistTopTracks(@Path("id") id: String): Call<List<Track>>

  @GET("/artist/{id}/albums")
  suspend fun getArtistAlbums(@Path("id") id: String): Call<List<Album>>
}