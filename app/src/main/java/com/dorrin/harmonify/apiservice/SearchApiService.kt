package com.dorrin.harmonify.apiservice

import com.dorrin.harmonify.model.DataList
import com.dorrin.harmonify.model.Track
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
  @GET("/search")
  suspend fun search(@Query("q") q: String, @Query("order") order: String): DataList<Track>
}