package kz.market.ui.components.snackbar


import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SnackBarType {
    Success,
    Error,
    Warning,
    Info,
    Neutral
}

enum class SnackBarDuration {
    Short,
    Long,
    Indefinite
}


class MarketSnackBar(
    private val hostState: MarketSnackBarHostState
) {
    fun showSnackBar(
        message: String,
        type: SnackBarType,
        actionLabel: String? = null,
        actionsOnNewLine: Boolean = false,
        onPerformAction: () -> Unit = {},
        withDismissAction: Boolean = false,
        duration: SnackBarDuration = if (withDismissAction) SnackBarDuration.Indefinite else SnackBarDuration.Short
    ) {
        hostState.dismiss()

        hostState.scope.launch {
            delay(100)
            hostState.showSnackBar(
                visuals = SnackBarVisuals(
                    type = type,
                    message = message,
                    actionLabel = actionLabel,
                    actionsOnNewLine = actionsOnNewLine,
                    performAction = onPerformAction,
                    withDismissAction = withDismissAction,
                    duration = duration
                )
            )
        }
    }
}
