package com.dorrin.harmonify

import android.app.Application
import androidx.work.Configuration
import com.dorrin.harmonify.worker.HarmonifyWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HarmonifyApplication : Application(), Configuration.Provider {
  @Inject
  lateinit var workerFactory: HarmonifyWorkerFactory

  override val workManagerConfiguration: Configuration
    get() = Configuration.Builder().setWorkerFactory(workerFactory).build()
}