@file:Suppress("unused")

package com.dorrin.harmonify.di

import com.dorrin.harmonify.permission.PermissionHandler
import com.dorrin.harmonify.permission.PermissionHandlerImpl
import com.dorrin.harmonify.receiver.AutoSleepReceiverStarter
import com.dorrin.harmonify.receiver.AutoSleepReceiverStarterImpl
import com.dorrin.harmonify.receiver.BedTimeReceiverStarter
import com.dorrin.harmonify.receiver.BedTimeReceiverStarterImpl
import com.dorrin.harmonify.view.settings.PreferenceManager
import com.dorrin.harmonify.view.settings.PreferenceManagerImpl
import com.dorrin.harmonify.view.settings.library.LibraryPreferences
import com.dorrin.harmonify.view.settings.library.LibraryPreferencesImpl
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
  @Singleton
  abstract fun bindsLibraryPreferences(impl: LibraryPreferencesImpl): LibraryPreferences

  @Binds
  abstract fun bindsPreferenceManager(impl: PreferenceManagerImpl): PreferenceManager

  @Binds
  abstract fun bindsPermissionHandler(impl: PermissionHandlerImpl): PermissionHandler

  @Binds
  abstract fun bindsBedTimeReceiverStarter(impl: BedTimeReceiverStarterImpl): BedTimeReceiverStarter

  @Binds
  abstract fun bindsAutoSleepReceiverStarter(impl: AutoSleepReceiverStarterImpl): AutoSleepReceiverStarter
}