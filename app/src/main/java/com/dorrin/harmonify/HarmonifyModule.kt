package com.dorrin.harmonify

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HarmonifyModule {
  @Provides
  @Singleton
  fun providesRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl("https://api.deezer.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
}