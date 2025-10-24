package kz.market.updater.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.market.updater.domain.model.UpdateMetaData
import kz.market.updater.domain.model.UpdateStatus
import java.io.File
import java.util.UUID

interface UpdateRepository {
    suspend fun fetchUpdateMetaData(): UpdateMetaData
    fun observeDownloadProgress(uuid: UUID): Flow<UpdateStatus>
    fun enqueueDownload(updateMetaData: UpdateMetaData): UUID
    fun installApk(apkFile: File, digest: String): Result<Unit>
}