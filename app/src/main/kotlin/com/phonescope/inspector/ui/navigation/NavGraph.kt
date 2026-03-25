package com.phonescope.inspector.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.phonescope.inspector.ui.dashboard.DashboardScreen
import com.phonescope.inspector.ui.monitor.MonitorScreen
import com.phonescope.inspector.ui.scan.ScanScreen
import com.phonescope.inspector.ui.settings.SettingsScreen
import com.phonescope.inspector.ui.process.ProcessListScreen
import com.phonescope.inspector.ui.properties.PropertiesScreen
import com.phonescope.inspector.ui.security.SecurityScreen
import com.phonescope.inspector.ui.theme.PhoneScopeTheme

@Composable
fun PhoneScopeNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Show bottom bar only on top-level destinations
    val showBottomBar = BottomNavTab.entries.any { tab ->
        currentDestination?.hasRoute(tab.route::class) == true
    }

    Scaffold(
        containerColor = PhoneScopeTheme.colors.background,
        bottomBar = {
            if (showBottomBar) {
                PhoneScopeBottomNavBar(
                    currentDestination = currentDestination,
                    onTabSelected = { tab ->
                        navController.navigate(tab.route) {
                            popUpTo(Screen.Dashboard) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                fadeIn(animationSpec = tween(300)) +
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300))
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) +
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(300))
            },
        ) {
            // ── Bottom Nav Destinations ──
            composable<Screen.Dashboard> { DashboardScreen() }
            composable<Screen.Scan> { ScanScreen() }
            composable<Screen.Monitor> { MonitorScreen() }
            composable<Screen.Settings> { SettingsScreen() }

            // ── Implemented Sub-Screens ──
            composable<Screen.ProcessList> { ProcessListScreen() }
            composable<Screen.Properties> { PropertiesScreen() }
            composable<Screen.Security> { SecurityScreen() }

            // ── Pro Feature Placeholders ──
            composable<Screen.CategoryList> { com.phonescope.inspector.ui.components.ComingSoonScreen("Categories") }
            composable<Screen.CategoryDetail> { com.phonescope.inspector.ui.components.ComingSoonScreen("Module Details") }
            composable<Screen.CpuGraph> { com.phonescope.inspector.ui.components.ComingSoonScreen("CPU Timeline") }
            composable<Screen.TemperatureMap> { com.phonescope.inspector.ui.components.ComingSoonScreen("Thermal Heatmap") }
            composable<Screen.RamGraph> { com.phonescope.inspector.ui.components.ComingSoonScreen("Memory Timeline") }
            composable<Screen.BatteryMonitor> { com.phonescope.inspector.ui.components.ComingSoonScreen("Battery Profiler") }
            composable<Screen.History> { com.phonescope.inspector.ui.components.ComingSoonScreen("Scan History") }
            composable<Screen.ScanDetail> { com.phonescope.inspector.ui.components.ComingSoonScreen("Scan Report") }
            composable<Screen.Compare> { com.phonescope.inspector.ui.components.ComingSoonScreen("Device Comparison") }
            composable<Screen.Report> { com.phonescope.inspector.ui.components.ComingSoonScreen("PDF Export") }
            composable<Screen.SoCVerify> { com.phonescope.inspector.ui.components.ComingSoonScreen("SoC Verification") }
            composable<Screen.AppAnalyzer> { com.phonescope.inspector.ui.components.ComingSoonScreen("App Analyzer") }
        }
    }
}
