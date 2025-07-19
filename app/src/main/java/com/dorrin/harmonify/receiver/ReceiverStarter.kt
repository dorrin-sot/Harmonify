package com.dorrin.harmonify.receiver

import android.content.Context

interface ReceiverStarter {
  fun start(context: Context)
  fun stop(context: Context)
}