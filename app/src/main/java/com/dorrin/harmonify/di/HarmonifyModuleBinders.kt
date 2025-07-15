@file:Suppress("unused")

package com.dorrin.harmonify.di

import com.dorrin.harmonify.view.settings.PreferenceManager
import com.dorrin.harmonify.view.settings.PreferenceManagerImpl
import com.dorrin.harmonify.view.settings.player.PlayerPreferences
import com.dorrin.harmonify.view.settings.player.PlayerPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HarmonifyModuleBinders {
  @Binds
  @Singleton
  abstract fun bindsPlayerPreferences(impl: PlayerPreferencesImpl): PlayerPreferences

  @Binds
  abstract fun bindsPreferenceManager(impl: PreferenceManagerImpl): PreferenceManager
}