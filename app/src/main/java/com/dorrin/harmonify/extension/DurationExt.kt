package com.dorrin.harmonify.extension

import kotlin.time.Duration

fun Duration.toComponentsHHMMSS(): Triple<Int, Int, Int> {
  var components = Triple(0, 0, 0)
  toComponents { h, m, s, _ -> components = Triple(h.toInt(), m, s) }
  return components
}
