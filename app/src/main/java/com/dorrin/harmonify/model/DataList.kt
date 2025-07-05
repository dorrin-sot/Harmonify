package com.dorrin.harmonify.model

data class DataList<out E>(
  val data: List<E>,
  val total: Int
)