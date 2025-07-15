package com.dorrin.harmonify.di

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.dorrin.harmonify.apiservice.AlbumApiService
import com.dorrin.harmonify.apiservice.ArtistApiService
import com.dorrin.harmonify.apiservice.ChartApiService
import com.dorrin.harmonify.apiservice.RetrofitHelper
import com.dorrin.harmonify.apiservice.SearchApiService
import com.dorrin.harmonify.apiservice.TrackApiService
import com.dorrin.harmonify.service.PlayerService
import com.google.common.util.concurrent.ListenableFuture
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HarmonifyModuleProviders {
  @Provides
  @Singleton
  fun providesAlbumService(): AlbumApiService = RetrofitHelper.albumApiService

  @Provides
  @Singleton
  fun providesArtistService(): ArtistApiService = RetrofitHelper.artistApiService

  @Provides
  @Singleton
  fun providesChartService(): ChartApiService = RetrofitHelper.chartApiService

  @Provides
  @Singleton
  fun providesSearchService(): SearchApiService = RetrofitHelper.searchApiService

  @Provides
  @Singleton
  fun providesTrackService(): TrackApiService = RetrofitHelper.trackApiService


  @Provides
  @Singleton
  fun providesMediaController(@ApplicationContext context: Context): ListenableFuture<MediaController> {
    val sessionToken = SessionToken(
      context,
      ComponentName(
        context,
        PlayerService::class.java
      )
    )
    return MediaController.Builder(context, sessionToken).buildAsync()
  }

  @Provides
  @Singleton
  fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
    context.getSharedPreferences("settings", Context.MODE_PRIVATE)
}