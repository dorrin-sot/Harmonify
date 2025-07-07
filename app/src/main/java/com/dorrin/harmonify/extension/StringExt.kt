package com.dorrin.harmonify.extension

fun String.capitalize(): String =
  mapIndexed { i, c ->
    if (i == 0) c.uppercase()
    else c.lowercase()
  }.joinToString("")