package kz.market.updater.domain.usecases

import kz.market.updater.domain.model.UpdateMetaData
import kz.market.updater.domain.repository.UpdateRepository
import javax.inject.Inject

class CheckUpdateUseCase @Inject constructor(
    private val updateRepository: UpdateRepository
) {
    suspend operator fun invoke(): UpdateMetaData = updateRepository.fetchUpdateMetaData()
}