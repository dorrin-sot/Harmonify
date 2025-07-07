package com.dorrin.harmonify.apiservice

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object RetrofitHelper {
  private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
      .baseUrl("https://api.deezer.com")
      .addConverterFactory(
        GsonConverterFactory.create(
          GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
              override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
              ): Date? {
                val dateString = json?.asString ?: ""
                return try {
                  SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
                    .parse(dateString)
                } catch (e: ParseException) {
                  null
                }
              }
            })
            .create()
        )
      )
      .build()
  }

  val albumApiService: AlbumApiService by lazy { retrofit.create(AlbumApiService::class.java) }
  val artistApiService: ArtistApiService by lazy { retrofit.create(ArtistApiService::class.java) }
  val chartApiService: ChartApiService by lazy { retrofit.create(ChartApiService::class.java) }
  val searchApiService: SearchApiService by lazy { retrofit.create(SearchApiService::class.java) }
  val trackApiService: TrackApiService by lazy { retrofit.create(TrackApiService::class.java) }
}