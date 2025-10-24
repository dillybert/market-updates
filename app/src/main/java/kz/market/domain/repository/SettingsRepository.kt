package kz.market.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.market.domain.model.ThemeOptions

interface SettingsRepository {
    suspend fun setThemeOption(themeOption: ThemeOptions)
    fun getThemeOption(): Flow<ThemeOptions>
}