package kz.market.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.market.domain.model.ThemeOptions
import kz.market.domain.repository.SettingsRepository
import javax.inject.Inject

private object PreferencesKeys {
    val THEME_OPTION = stringPreferencesKey("theme_option")
}

class SettingsRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SettingsRepository {
    private val Context.dataStore by preferencesDataStore(name = "settings")

    private val dataStore: Flow<ThemeOptions> =
        context.dataStore.data.map { preferences ->
            when (preferences[PreferencesKeys.THEME_OPTION]) {
                ThemeOptions.SYSTEM.name -> ThemeOptions.SYSTEM
                ThemeOptions.LIGHT.name -> ThemeOptions.LIGHT
                ThemeOptions.DARK.name -> ThemeOptions.DARK
                else -> ThemeOptions.SYSTEM
            }
        }

    override suspend fun setThemeOption(themeOption: ThemeOptions) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_OPTION] = themeOption.name
        }
    }

    override fun getThemeOption(): Flow<ThemeOptions> = dataStore
}