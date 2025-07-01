package com.dorrin.harmonify.service

import com.dorrin.harmonify.model.Track
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
  @GET("/search")
  suspend fun search(@Query("q") q: String, @Query("order") order: String): Call<List<Track>>
}