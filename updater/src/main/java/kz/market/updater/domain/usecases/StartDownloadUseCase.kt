package kz.market.updater.domain.usecases

import kz.market.updater.domain.model.UpdateMetaData
import kz.market.updater.domain.repository.UpdateRepository
import java.util.UUID
import javax.inject.Inject

class StartDownloadUseCase @Inject constructor(
    private val updateRepository: UpdateRepository
) {
    operator fun invoke(updateMetaData: UpdateMetaData): UUID = updateRepository.enqueueDownload(updateMetaData)
}