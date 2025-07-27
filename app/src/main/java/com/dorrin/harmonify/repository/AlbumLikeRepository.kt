package com.dorrin.harmonify.repository

import com.dorrin.harmonify.dao.AlbumDao
import com.dorrin.harmonify.entities.AlbumLike
import com.dorrin.harmonify.worker.album.AlbumDownloadWorkerManager
import javax.inject.Inject

class AlbumLikeRepository @Inject constructor(
  private val dao: AlbumDao,
  private val downloadWorker: AlbumDownloadWorkerManager
) : LikeableRepository<AlbumLike> {

  override fun getAll() = dao.getAll()

  override fun find(id: Long) = dao.find(id)

  override fun add(item: AlbumLike) {
    dao.add(item)
  }

  override fun remove(item: AlbumLike) {
    dao.remove(item)
  }

  override fun update(item: AlbumLike) = dao.update(item)

  override fun scheduleDownload(item: AlbumLike) = downloadWorker.schedule(item.album)

  override fun cleanup(item: AlbumLike) = downloadWorker.cleanup(item.album)
}
