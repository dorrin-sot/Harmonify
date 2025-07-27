package com.dorrin.harmonify.repository

import com.dorrin.harmonify.dao.TrackDao
import com.dorrin.harmonify.entities.TrackLike
import com.dorrin.harmonify.worker.track.TrackDownloadWorkerManager
import javax.inject.Inject

class TrackLikeRepository @Inject constructor(
  private val dao: TrackDao,
  private val downloadWorker: TrackDownloadWorkerManager
) : LikeableRepository<TrackLike> {
  override fun getAll() = dao.getAll()

  override fun find(id: Long) = dao.find(id)

  override fun add(item: TrackLike) = dao.add(item)

  override fun remove(item: TrackLike) = dao.remove(item)

  override fun update(item: TrackLike) = dao.update(item)

  override fun scheduleDownload(item: TrackLike) = downloadWorker.schedule(item.track)

  override fun cleanup(item: TrackLike) = downloadWorker.cleanup(item.track)
}
