package com.dorrin.harmonify.extension

fun String.capitalize(): String =
  replaceFirstChar { it.uppercase() }