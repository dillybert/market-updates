package kz.market.updater.domain.usecases

import kotlinx.coroutines.flow.Flow
import kz.market.updater.domain.model.UpdateStatus
import kz.market.updater.domain.repository.UpdateRepository
import java.util.UUID
import javax.inject.Inject

class ObserveDownloadUseCase @Inject constructor(
    private val updateRepository: UpdateRepository
) {
    operator fun invoke(uuid: UUID): Flow<UpdateStatus> = updateRepository.observeDownloadProgress(uuid)
}