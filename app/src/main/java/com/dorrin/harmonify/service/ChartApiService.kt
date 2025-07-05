package com.dorrin.harmonify.service

import com.dorrin.harmonify.model.Chart
import retrofit2.Call
import retrofit2.http.GET

interface ChartApiService {
  @GET("/chart")
  suspend fun getChart(): Chart
}