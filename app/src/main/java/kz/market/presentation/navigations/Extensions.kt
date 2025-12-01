package kz.market.presentation.navigations

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController

internal fun NavHostController.navigateTo(
    destination: NavigationDestination,
    inclusive: Boolean = false
) {
    navigate(destination) {
        launchSingleTop = true
        restoreState = true

        if (inclusive) {
            popUpTo(graph.startDestinationId) {
                saveState = true
            }
        }
    }
}

internal fun NavDestination?.isDestinationInHierarchy(destination: NavigationDestination): Boolean {
    return this?.hierarchy?.any { it.route == destination::class.qualifiedName } == true
}