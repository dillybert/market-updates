package kz.market.presentation.navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kz.market.logger.ApplicationEvent
import kz.market.logger.LocalLogger

@Composable
fun AnalyticsNavObserver(navController: NavHostController) {
    val logger = LocalLogger.current

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            val args = destination.arguments.mapValues { it.value.toString() }
            logger.log(ApplicationEvent.Screen(destination.route ?: "unknown", args))
        }

        navController.addOnDestinationChangedListener(listener = listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}