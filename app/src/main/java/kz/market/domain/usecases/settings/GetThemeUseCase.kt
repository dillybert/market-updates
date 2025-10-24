package kz.market.domain.usecases.settings

import kz.market.domain.repository.SettingsRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getThemeOption()
}