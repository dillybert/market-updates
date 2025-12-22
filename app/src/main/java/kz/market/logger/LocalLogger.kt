package kz.market.logger

import androidx.compose.runtime.staticCompositionLocalOf

val LocalLogger = staticCompositionLocalOf<ApplicationLogger> {
    error("No logger provided")
}