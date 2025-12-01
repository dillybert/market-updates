package kz.market.ui.components.snackbar


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.market.ui.icons.MarketIcons
import kz.market.ui.theme.snackBarError
import kz.market.ui.theme.snackBarInfo
import kz.market.ui.theme.snackBarOnError
import kz.market.ui.theme.snackBarOnInfo
import kz.market.ui.theme.snackBarOnSuccess
import kz.market.ui.theme.snackBarOnWarning
import kz.market.ui.theme.snackBarSuccess
import kz.market.ui.theme.snackBarWarning

internal data class SnackBarColorSet(
    val container: Color,
    val onContainer: Color,
    val outline: Color
)

internal data class SnackBarColors(
    val success: SnackBarColorSet,
    val error: SnackBarColorSet,
    val warning: SnackBarColorSet,
    val info: SnackBarColorSet,
    val neutral: SnackBarColorSet
)

internal data class SnackBarIcons(
    val success: Painter,
    val error: Painter,
    val warning: Painter,
    val info: Painter
)

object MarketSnackBarDefaults {
    val IconSize: Dp = 20.dp
    val IconSpacing: Dp = 10.dp

    val ButtonHeight = 36.dp
    val IconButtonSize = 14.dp

    val ActionButtonStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium
    val Shape
        @Composable get() = MaterialTheme.shapes.medium
    val TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium

    @Composable
    internal fun colors(
        success: SnackBarColorSet = SnackBarColorSet(
            container = snackBarSuccess,
            onContainer = snackBarOnSuccess,
            outline = Color.Transparent
        ),

        error: SnackBarColorSet = SnackBarColorSet(
            container = snackBarError,
            onContainer = snackBarOnError,
            outline = Color.Transparent
        ),

        warning: SnackBarColorSet = SnackBarColorSet(
            container = snackBarWarning,
            onContainer = snackBarOnWarning,
            outline = Color.Transparent
        ),

        info: SnackBarColorSet = SnackBarColorSet(
            container = snackBarInfo,
            onContainer = snackBarOnInfo,
            outline = Color.Transparent
        ),

        neutral: SnackBarColorSet = SnackBarColorSet(
            container = MaterialTheme.colorScheme.inverseSurface,
            onContainer = MaterialTheme.colorScheme.inverseOnSurface,
            outline = Color.Transparent
        )
    ): SnackBarColors =
        SnackBarColors(
            success = success,
            error = error,
            warning = warning,
            info = info,
            neutral = neutral
        )

    @Composable
    internal fun icons(
        success: Painter = MarketIcons.Check_Contained,
        error: Painter = MarketIcons.X_Circle_Contained,
        warning: Painter = MarketIcons.Help_Circle_Contained,
        info: Painter = MarketIcons.Information_Circle_Contained
    ): SnackBarIcons =
        SnackBarIcons(
            success = success,
            error = error,
            warning = warning,
            info = info
        )
}