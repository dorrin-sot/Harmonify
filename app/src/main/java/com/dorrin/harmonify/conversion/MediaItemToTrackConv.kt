package com.dorrin.harmonify.conversion

import androidx.media3.common.MediaItem
import com.dorrin.harmonify.model.Track

fun MediaItem.toTrack(): Track? = localConfiguration?.tag as? Track