package com.phonescope.inspector.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.DocumentScanner
import androidx.compose.material.icons.rounded.Monitor
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

// ═══════════════════════════════════════════
// Route definitions using type-safe navigation
// ═══════════════════════════════════════════

sealed interface Screen {

    // Bottom nav destinations
    @Serializable data object Dashboard : Screen
    @Serializable data object Scan : Screen
    @Serializable data object Monitor : Screen
    @Serializable data object Settings : Screen

    // Category screens
    @Serializable data object CategoryList : Screen
    @Serializable data class CategoryDetail(val moduleId: Int) : Screen

    // Monitor sub-screens
    @Serializable data object CpuGraph : Screen
    @Serializable data object TemperatureMap : Screen
    @Serializable data object RamGraph : Screen
    @Serializable data object BatteryMonitor : Screen
    @Serializable data object ProcessList : Screen

    // Feature screens
    @Serializable data object History : Screen
    @Serializable data class ScanDetail(val scanId: Long) : Screen
    @Serializable data object Compare : Screen
    @Serializable data object Report : Screen
    @Serializable data object Security : Screen
    @Serializable data object SoCVerify : Screen
    @Serializable data object AppAnalyzer : Screen
    @Serializable data object Properties : Screen
}

/**
 * Bottom navigation bar tab definitions.
 */
enum class BottomNavTab(
    val label: String,
    val icon: ImageVector,
    val route: Screen,
) {
    DASHBOARD("Dashboard", Icons.Rounded.Dashboard, Screen.Dashboard),
    SCAN("Scan", Icons.Rounded.DocumentScanner, Screen.Scan),
    MONITOR("Monitor", Icons.Rounded.Monitor, Screen.Monitor),
    SETTINGS("Settings", Icons.Rounded.Settings, Screen.Settings),
}
