package kz.market.updater.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.market.updater.domain.model.UpdateStatus
import kz.market.updater.domain.repository.UpdateRepository
import java.io.File
import javax.inject.Inject

class InstallApkUseCase @Inject constructor(
    private val updateRepository: UpdateRepository
) {
    operator fun invoke(apkFile: File, digest: String): Flow<UpdateStatus> = flow {
        val result = updateRepository.installApk(apkFile, digest)

        if (result.isSuccess) {
            emit(UpdateStatus.Installing)
        } else {
            emit(UpdateStatus.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
        }
    }
}