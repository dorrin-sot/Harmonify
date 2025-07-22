package com.dorrin.harmonify.di

import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.room.Room
import com.dorrin.harmonify.HarmonifyDatabase
import com.dorrin.harmonify.apiservice.AlbumApiService
import com.dorrin.harmonify.apiservice.ArtistApiService
import com.dorrin.harmonify.apiservice.ChartApiService
import com.dorrin.harmonify.apiservice.RetrofitHelper
import com.dorrin.harmonify.apiservice.SearchApiService
import com.dorrin.harmonify.apiservice.TrackApiService
import com.dorrin.harmonify.dao.AlbumDao
import com.dorrin.harmonify.dao.ArtistDao
import com.dorrin.harmonify.dao.TrackDao
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
  fun providesAlbumService(): AlbumApiService = RetrofitHelper.albumApiService

  @Provides
  fun providesArtistService(): ArtistApiService = RetrofitHelper.artistApiService

  @Provides
  fun providesChartService(): ChartApiService = RetrofitHelper.chartApiService

  @Provides
  fun providesSearchService(): SearchApiService = RetrofitHelper.searchApiService

  @Provides
  fun providesTrackService(): TrackApiService = RetrofitHelper.trackApiService

  @Provides
  @Singleton
  fun providesDatabase(@ApplicationContext context: Context): HarmonifyDatabase =
    Room.databaseBuilder(
      context,
      HarmonifyDatabase::class.java,
      "harmonify-db"
    ).build()

  @Provides
  fun providesAlbumDao(@ApplicationContext context: Context): AlbumDao =
    providesDatabase(context).albumDao()

  @Provides
  fun providesArtistDao(@ApplicationContext context: Context): ArtistDao =
    providesDatabase(context).artistDao()

  @Provides
  fun providesTrackDao(@ApplicationContext context: Context): TrackDao =
    providesDatabase(context).trackDao()

  @Provides
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