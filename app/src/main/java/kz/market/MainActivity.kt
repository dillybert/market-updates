package kz.market

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.market.domain.model.ThemeOptions
import kz.market.presentation.navigations.BottomNavigationDashboardDestination
import kz.market.presentation.navigations.BottomNavigationExpensesDestination
import kz.market.presentation.navigations.BottomNavigationPanelDestination
import kz.market.presentation.navigations.BottomNavigationSalesDestination
import kz.market.presentation.navigations.BottomNavigationWarehouseDestination
import kz.market.presentation.navigations.NavigationGraph
import kz.market.presentation.navigations.isDestinationInHierarchy
import kz.market.presentation.navigations.navigateTo
import kz.market.presentation.screens.settings.viewmodel.SettingsViewModel
import kz.market.ui.components.snackbar.MarketSnackBarHost
import kz.market.ui.components.snackbar.rememberMarketSnackBar
import kz.market.ui.components.snackbar.rememberMarketSnackBarHostState
import kz.market.ui.icons.MarketIcons
import kz.market.ui.preview.ThemedPreview
import kz.market.ui.theme.MarketTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val settingsViewModel: SettingsViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            settingsViewModel.themeOption.value == null
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Root()
        }
    }
}

@Composable
fun RootContent(
    themeOption: ThemeOptions
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val snackBarHostState = rememberMarketSnackBarHostState()
    val snackBar = rememberMarketSnackBar(snackBarHostState)

    val showBottomBar = currentDestination.isDestinationInHierarchy(BottomNavigationPanelDestination) ||
            currentDestination.isDestinationInHierarchy(BottomNavigationSalesDestination) ||
            currentDestination.isDestinationInHierarchy(BottomNavigationWarehouseDestination) ||
            currentDestination.isDestinationInHierarchy(BottomNavigationExpensesDestination) ||
            currentDestination.isDestinationInHierarchy(BottomNavigationDashboardDestination)

    MarketTheme(
       themeOption = themeOption
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),

            snackbarHost = {
                MarketSnackBarHost(
                    hostState = snackBarHostState
                )
            },

            bottomBar = {
                AnimatedVisibility(
                    visible = showBottomBar,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeIn(animationSpec = tween(durationMillis = 300)),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 300)
                    ) + fadeOut(animationSpec = tween(durationMillis = 300))
                ) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentDestination?.isDestinationInHierarchy(
                                BottomNavigationPanelDestination
                            ) == true,
                            onClick = { navController.navigateTo(BottomNavigationPanelDestination, inclusive = true) },
                            icon = {
                                Icon(
                                    painter = MarketIcons.File_03,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    stringResource(R.string.bottom_bar_panel),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        )

                        NavigationBarItem(
                            selected = currentDestination?.isDestinationInHierarchy(
                                BottomNavigationSalesDestination
                            ) == true,
                            onClick = { navController.navigateTo(BottomNavigationSalesDestination, inclusive = true) },
                            icon = {
                                Icon(
                                    painter = MarketIcons.Cart,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    stringResource(R.string.bottom_bar_sales),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        )

                        NavigationBarItem(
                            selected = currentDestination?.isDestinationInHierarchy(
                                BottomNavigationWarehouseDestination
                            ) == true,
                            onClick = { navController.navigateTo(BottomNavigationWarehouseDestination, inclusive = true) },
                            icon = {
                                Icon(
                                    painter = MarketIcons.Package_01,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    stringResource(R.string.bottom_bar_warehouse),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        )

                        NavigationBarItem(
                            selected = currentDestination?.isDestinationInHierarchy(
                                BottomNavigationExpensesDestination
                            ) == true,
                            onClick = { navController.navigateTo(BottomNavigationExpensesDestination, inclusive = true) },
                            icon = {
                                Icon(
                                    painter = MarketIcons.Wallet_02,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    stringResource(R.string.bottom_bar_expenses),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        )

                        NavigationBarItem(
                            selected = currentDestination?.isDestinationInHierarchy(
                                BottomNavigationDashboardDestination
                            ) == true,
                            onClick = { navController.navigateTo(BottomNavigationDashboardDestination, inclusive = true) },
                            icon = {
                                Icon(
                                    painter = MarketIcons.BarChartSquare_03,
                                    contentDescription = null
                                )
                            },
                            label = {
                                Text(
                                    stringResource(R.string.bottom_bar_dashboard),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavigationGraph(
                navController = navController,
                snackBar = snackBar,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
            )
        }
    }
}

@Composable
fun Root(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
   val themeOption = settingsViewModel.themeOption.collectAsState()

   if (themeOption.value != null)
       RootContent(themeOption = themeOption.value!!)
   else
       RootContent(themeOption = ThemeOptions.SYSTEM)
}

@ThemedPreview
@Composable
fun GreetingPreview() {
    RootContent(
        themeOption = ThemeOptions.SYSTEM
    )
}