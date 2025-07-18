package com.dorrin.harmonify.apiservice

import com.dorrin.harmonify.model.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface ChartApiService {
  @GET("/chart/{genre}")
  suspend fun getChart(@Path("genre") genre: Long = 132): Result
}