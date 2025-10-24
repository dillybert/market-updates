package kz.market.ui.components.snackbar

data class SnackBarVisuals(
    val type: SnackBarType,
    val actionsOnNewLine: Boolean,
    val message: String,
    val actionLabel: String?,
    val performAction: () -> Unit,
    val withDismissAction: Boolean,
    val duration: SnackBarDuration
)

fun SnackBarVisuals.toMillis(): Long = when (duration) {
    SnackBarDuration.Indefinite -> Long.MAX_VALUE
    SnackBarDuration.Long -> 10_000L
    SnackBarDuration.Short -> 5_000L
}