package com.dorrin.harmonify.repository

import kotlinx.coroutines.flow.Flow

abstract class LikeableRepository<T> {
  abstract val allItems: Flow<List<T>>
  abstract fun find(id: Long): T?
  abstract fun add(item: T)
  abstract fun remove(item: T)
  abstract fun update(item: T)

  abstract fun scheduleDownload(item: T)
  abstract fun cleanup(item: T)

  fun addWithPostOp(item: T) {
    add(item)
    scheduleDownload(item)
  }

  fun removeWithPostOp(item: T) {
    remove(item)
    cleanup(item)
  }

  fun isLiked(id: Long): Boolean = find(id) != null

  fun toggleLiked(id: Long, item: T) = setLiked(item, !isLiked(id))

  fun setLiked(item: T, value: Boolean) =
    if (!value) removeWithPostOp(item)
    else addWithPostOp(item)
}
