package kz.market.ui.components.snackbar


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberMarketSnackBarHostState(
    scope: CoroutineScope = rememberCoroutineScope()
): MarketSnackBarHostState = remember(scope) { MarketSnackBarHostState(scope) }

@Composable
fun rememberMarketSnackBar(
    hostState: MarketSnackBarHostState
): MarketSnackBar = remember(hostState) { MarketSnackBar(hostState) }