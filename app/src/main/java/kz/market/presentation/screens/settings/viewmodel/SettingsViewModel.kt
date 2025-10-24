package kz.market.presentation.screens.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kz.market.domain.model.ThemeOptions
import kz.market.domain.usecases.settings.GetThemeUseCase
import kz.market.domain.usecases.settings.SetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setThemeUseCase: SetThemeUseCase,
    getThemeUseCase: GetThemeUseCase
) : ViewModel() {
    val themeOption =
        getThemeUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    fun setTheme(themeOption: ThemeOptions) =
        viewModelScope.launch {
            setThemeUseCase(themeOption)
        }
}