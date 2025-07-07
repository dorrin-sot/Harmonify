package com.dorrin.harmonify

import com.dorrin.harmonify.apiservice.AlbumApiService
import com.dorrin.harmonify.apiservice.ArtistApiService
import com.dorrin.harmonify.apiservice.ChartApiService
import com.dorrin.harmonify.apiservice.RetrofitHelper
import com.dorrin.harmonify.apiservice.SearchApiService
import com.dorrin.harmonify.apiservice.TrackApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object HarmonifyModule {
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
}