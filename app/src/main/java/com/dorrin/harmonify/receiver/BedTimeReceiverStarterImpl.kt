package com.dorrin.harmonify.receiver

import android.content.Context
import android.content.IntentFilter
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import javax.inject.Inject

class BedTimeReceiverStarterImpl @Inject constructor() : BedTimeReceiverStarter {
  private var bedTimeReceiver: BedTimeReceiver? = null

  override fun start(context: Context) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    if (bedTimeReceiver != null) return

    bedTimeReceiver = BedTimeReceiver()

    IntentFilter().run {
      addAction("android.app.action.INTERRUPTION_FILTER_CHANGED")

      registerReceiver(context, bedTimeReceiver, this, RECEIVER_EXPORTED)
    }
  }

  override fun stop(context: Context) {
    object {}.javaClass.apply { println("${enclosingClass?.name}::${enclosingMethod?.name}") }

    bedTimeReceiver?.run {
      context.unregisterReceiver(this)
      bedTimeReceiver = null
    }
  }
}