package com.dorrin.harmonify.repository

import com.dorrin.harmonify.dao.ArtistDao
import com.dorrin.harmonify.entities.ArtistLike
import javax.inject.Inject

class ArtistLikeRepository @Inject constructor(
  private val dao: ArtistDao,
) : LikeableRepository<ArtistLike> {
  override fun getAll() = dao.getAll()

  override fun find(id: Long) = dao.find(id)

  override fun add(item: ArtistLike) = dao.add(item)

  override fun remove(item: ArtistLike) = dao.remove(item)

  override fun update(item: ArtistLike) = dao.update(item)

  override fun scheduleDownload(item: ArtistLike) {
    // TODO 
  }

  override fun cleanup(item: ArtistLike) {
    // TODO 
  }
}
