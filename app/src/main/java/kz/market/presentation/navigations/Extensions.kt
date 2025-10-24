package kz.market.presentation.navigations

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController

internal fun NavHostController.navigateTo(destination: NavigationDestination) {
    navigate(destination) {
        popUpTo(graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

internal fun NavDestination?.isDestinationInHierarchy(destination: NavigationDestination): Boolean {
    return this?.hierarchy?.any { it.route == destination::class.qualifiedName } == true
}