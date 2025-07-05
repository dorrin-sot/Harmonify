package com.dorrin.harmonify

import com.dorrin.harmonify.service.AlbumApiService
import com.dorrin.harmonify.service.ArtistApiService
import com.dorrin.harmonify.service.ChartApiService
import com.dorrin.harmonify.service.RetrofitHelper
import com.dorrin.harmonify.service.SearchApiService
import com.dorrin.harmonify.service.TrackApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
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