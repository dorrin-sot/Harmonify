package com.dorrin.harmonify.apiservice

import com.dorrin.harmonify.model.Chart
import retrofit2.http.GET

interface ChartApiService {
  @GET("/chart")
  suspend fun getChart(): Chart
}