package com.dorrin.harmonify.service

import com.dorrin.harmonify.model.Album
import com.dorrin.harmonify.model.Track
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {
  @GET("/album/{id}")
  suspend fun getAlbum(@Path("id") id: String): Call<Album>

  @GET("/album/{id}/tracks")
  suspend fun getAlbumTracks(@Path("id") id: String): Call<List<Track>>
}