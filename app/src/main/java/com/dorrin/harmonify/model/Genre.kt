package com.dorrin.harmonify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
  val id: Long,
  val name: String,

  val picture: String,
  val pictureSmall: String,
  val pictureMedium: String,
  val pictureBig: String,
  val pictureXl: String
) : Parcelable