package kz.market.presentation.navigations

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kz.market.presentation.screens.dashboard.DashboardScreen
import kz.market.presentation.screens.expenses.ExpensesScreen
import kz.market.presentation.screens.panel.PanelScreen
import kz.market.presentation.screens.sales.SalesScreen
import kz.market.presentation.screens.settings.SettingsScreen
import kz.market.presentation.screens.update.UpdateScreen
import kz.market.presentation.screens.warehouse.WarehouseScreen
import kz.market.ui.components.snackbar.MarketSnackBar

@Composable
fun NavigationGraph(
    navController: NavHostController,
    snackBar: MarketSnackBar,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationPanelDestination::class,
        modifier = modifier,

        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) },
        popEnterTransition = { fadeIn(animationSpec = tween(300)) },
        popExitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        bottomNavigationPanelNavGraph(navController, snackBar)
        bottomNavigationSalesNavGraph(navController, snackBar)
        bottomNavigationWarehouseNavGraph(navController, snackBar)
        bottomNavigationExpensesNavGraph(navController, snackBar)
        bottomNavigationDashboardNavGraph(navController, snackBar)

        settingsNavGraph(navController, snackBar)
    }
}

fun NavGraphBuilder.bottomNavigationPanelNavGraph(
    navController: NavHostController,
    snackBar: MarketSnackBar
) {
    composable<BottomNavigationPanelDestination> {
        PanelScreen(
            modifier = Modifier,
            snackBar = snackBar,
            onActionClick = {
                navController.navigateTo(SettingsRootDestination)
            }
        )
    }
}

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController,
    snackBar: MarketSnackBar
) {
    navigation<SettingsRootDestination>(
        startDestination = SettingsDestination
    ) {
        composable<SettingsDestination> {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onUpdateClick = {
                    navController.navigateTo(UpdateDestination)
                }
            )
        }

        composable<UpdateDestination> {
            UpdateScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun NavGraphBuilder.bottomNavigationSalesNavGraph(
    navController: NavHostController,
    snackBar: MarketSnackBar
) {
    composable<BottomNavigationSalesDestination> {
        SalesScreen(modifier = Modifier)
    }
}


fun NavGraphBuilder.bottomNavigationWarehouseNavGraph(
    navController: NavHostController,
    snackBar: MarketSnackBar
) {
    composable<BottomNavigationWarehouseDestination> {
        WarehouseScreen(modifier = Modifier)
    }
}


fun NavGraphBuilder.bottomNavigationExpensesNavGraph(
    navController: NavHostController,
    snackBar: MarketSnackBar
) {
    composable<BottomNavigationExpensesDestination> {
        ExpensesScreen(modifier = Modifier)
    }
}

fun NavGraphBuilder.bottomNavigationDashboardNavGraph(
    navController: NavHostController,
    snackBar: MarketSnackBar
) {
    composable<BottomNavigationDashboardDestination> {
        DashboardScreen(modifier = Modifier)
    }
}