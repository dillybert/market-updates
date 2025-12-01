package kz.market.ui.components.snackbar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Custom SnackbarHostState implementation with manual control over duration.
 */
class MarketSnackBarHostState(
    val scope: CoroutineScope
) {
    private var currentJob: Job? = null
    private val _currentVisuals = mutableStateOf<SnackBarVisuals?>(null)
    val currentVisuals: State<SnackBarVisuals?> = _currentVisuals

    fun showSnackBar(visuals: SnackBarVisuals) {
        if (_currentVisuals.value == visuals) return

        currentJob?.cancel()
        _currentVisuals.value = visuals

        if (visuals.duration == SnackBarDuration.Indefinite) return

        currentJob = scope.launch {
            delay(visuals.toMillis())
            dismiss()
        }
    }


    fun dismiss() {
        _currentVisuals.value = null
        currentJob?.cancel()
        currentJob = null
    }
}
