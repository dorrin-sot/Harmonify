package com.dorrin.harmonify.repository

import kotlinx.coroutines.flow.Flow

interface LikeableRepository<T> {
  fun getAll(): Flow<List<T>>
  fun find(id: Long): T?
  fun add(item: T)
  fun remove(item: T)
  fun update(item: T)

  fun scheduleDownload(item: T)
  fun cleanup(item: T)

  fun addWithPostOp(item: T) {
    add(item)
    scheduleDownload(item)
  }

  fun removeWithPostOp(item: T) {
    remove(item)
    cleanup(item)
  }

  fun isLiked(id: Long): Boolean = find(id) != null

  fun toggleLiked(id: Long, item: T) {
    if (isLiked(id)) removeWithPostOp(item)
    else addWithPostOp(item)
  }
}
