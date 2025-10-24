package kz.market.presentation.navigations

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

interface NavigationDestination


// --- Bottom navigation and Main screens---
@Keep
@Serializable
object BottomNavigationPanelDestination : NavigationDestination
@Keep
@Serializable
object BottomNavigationSalesDestination : NavigationDestination
@Keep
@Serializable
object BottomNavigationWarehouseDestination : NavigationDestination
@Keep
@Serializable
object BottomNavigationExpensesDestination : NavigationDestination
@Keep
@Serializable
object BottomNavigationDashboardDestination : NavigationDestination



// --- Inner Screens ---
@Keep
@Serializable
object SettingsDestination : NavigationDestination