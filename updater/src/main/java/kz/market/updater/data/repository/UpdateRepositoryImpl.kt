package kz.market.updater.data.repository

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kz.market.updater.data.config.UpdateDefaults
import kz.market.updater.data.installer.ApkInstallerImpl
import kz.market.updater.data.remote.GitHubUpdateDataSource
import kz.market.updater.data.worker.UpdateDownloadWorker
import kz.market.updater.domain.model.UpdateMetaData
import kz.market.updater.domain.model.UpdateStatus
import kz.market.updater.domain.repository.UpdateRepository
import java.io.File
import java.util.UUID
import javax.inject.Inject

class UpdateRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val githubUpdateDataSource: GitHubUpdateDataSource,
    private val workManager: WorkManager,
    private val apkInstaller: ApkInstallerImpl
): UpdateRepository {
    override suspend fun fetchUpdateMetaData(): UpdateMetaData =
        githubUpdateDataSource.fetchUpdateMetaData()

    override fun observeDownloadProgress(uuid: UUID): Flow<UpdateStatus> {
        return workManager.getWorkInfoByIdLiveData(uuid)
            .asFlow()
            .mapNotNull { workInfo ->
                workInfo?.let {
                    when (it.state) {
                        WorkInfo.State.ENQUEUED,
                        WorkInfo.State.RUNNING -> {
                            val progress = it.progress.getInt(UpdateDefaults.KEY_PROGRESS, 0)
                            val downloaded = it.progress.getLong(UpdateDefaults.KEY_DOWNLOADED_BYTES, 0)
                            val contentSize = it.progress.getLong(UpdateDefaults.KEY_TOTAL_BYTES, 0)

                            UpdateStatus.Downloading(
                                totalBytes = contentSize,
                                progress = progress,
                                downloadedBytes = downloaded
                            )
                        }

                        WorkInfo.State.SUCCEEDED -> {
                            val apkFile = it.outputData.getString(UpdateDefaults.KEY_APK_FILE_PATH)
                            val apkDigest = it.outputData.getString(UpdateDefaults.KEY_APK_DIGEST)

                            if (apkFile != null && apkDigest != null) {
                                UpdateStatus.Downloaded(
                                    File(apkFile),
                                    apkDigest
                                )
                            } else {
                                UpdateStatus.Error(message = "APK FILE OR DIGEST is null")
                            }
                        }

                        WorkInfo.State.CANCELLED,
                        WorkInfo.State.FAILED,
                        WorkInfo.State.BLOCKED -> {
                            val error = it.outputData.getString(UpdateDefaults.KEY_ERROR)

                            UpdateStatus.Error(error ?: "Unknown error")
                        }
                    }
                }
            }
    }

    override fun enqueueDownload(updateMetaData: UpdateMetaData): UUID {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<UpdateDownloadWorker>()
            .setInputData(
                workDataOf(
                    UpdateDefaults.KEY_APK_URL to updateMetaData.apkUrl,
                    UpdateDefaults.KEY_APK_DIGEST to updateMetaData.apkDigest
                )
            )
            .setConstraints(constraints = constraints)
            .build()

        workManager.enqueueUniqueWork(
            UpdateDefaults.UNIQUE_WORK_NAME, ExistingWorkPolicy.REPLACE, workRequest
        )

        return workRequest.id
    }

    override fun installApk(apkFile: File, digest: String): Result<Unit> {
        return apkInstaller.installApk(apkFile, digest)
    }
}