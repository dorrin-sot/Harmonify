package com.dorrin.harmonify.service

import com.dorrin.harmonify.model.Track
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackApiService {
  @GET("/track/{id}")
  suspend fun getTrack(@Path("id") id: String): Call<Track>
}