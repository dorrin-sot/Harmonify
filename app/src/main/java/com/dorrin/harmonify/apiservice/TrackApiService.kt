package com.dorrin.harmonify.apiservice

import com.dorrin.harmonify.model.Track
import retrofit2.http.GET
import retrofit2.http.Path

interface TrackApiService {
  @GET("/track/{id}")
  suspend fun getTrack(@Path("id") id: Long): Track
}