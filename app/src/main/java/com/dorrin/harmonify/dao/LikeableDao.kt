package com.dorrin.harmonify.dao

import kotlinx.coroutines.flow.Flow

interface LikeableDao<T> {
  fun getAll(): Flow<List<T>>

  fun find(id: Long): T?

  fun add(item: T)

  fun remove(item: T)

  fun update(item: T)
}