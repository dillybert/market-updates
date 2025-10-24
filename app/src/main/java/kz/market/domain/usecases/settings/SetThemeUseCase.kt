package kz.market.domain.usecases.settings

import kz.market.domain.model.ThemeOptions
import kz.market.domain.repository.SettingsRepository
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(themeOption: ThemeOptions) =
        settingsRepository.setThemeOption(themeOption)
}