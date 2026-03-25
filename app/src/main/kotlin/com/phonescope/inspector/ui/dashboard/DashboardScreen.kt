package com.phonescope.inspector.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.phonescope.inspector.ui.components.HardwareMeter
import com.phonescope.inspector.ui.theme.*

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PhoneScopeTheme.colors.background)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = PrimaryCyan
            )
        } else {
            AnimatedVisibility(visible = true, enter = fadeIn()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    // ─── Header ───
                    DashboardHeader(state)

                    Spacer(Modifier.height(20.dp))

                    // ─── Live Vitals Grid ───
                    SectionTitle("Live Vitals")
                    Spacer(Modifier.height(10.dp))
                    VitalsGrid(state)

                    Spacer(Modifier.height(24.dp))

                    // ─── Hardware Specs ───
                    SectionTitle("Hardware Specs")
                    Spacer(Modifier.height(10.dp))

                    // CPU Card
                    state.cpuData?.let { cpu ->
                        val coreCount = cpu.processors.size
                        val arch = cpu.global["model name"] ?: cpu.global["Hardware"] ?: "Unknown SoC"
                        SpecCard(
                            icon = Icons.Rounded.Memory,
                            title = "CPU",
                            subtitle = arch,
                            accentColor = PrimaryCyan,
                            details = listOf(
                                "Cores" to "$coreCount",
                                "Architecture" to (state.kernelData?.machine ?: "—"),
                                "Governor" to (cpu.processors.firstOrNull()?.governor ?: "—"),
                            )
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    // GPU Card
                    state.gpuData?.let { gpu ->
                        SpecCard(
                            icon = Icons.Rounded.Videocam,
                            title = "GPU",
                            subtitle = "${gpu.vendor} ${gpu.model}",
                            accentColor = SecondaryAmber,
                            details = listOf(
                                "Max Freq" to if (gpu.maxFreq > 0) "${gpu.maxFreq / 1000000} MHz" else "—",
                            )
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    // Kernel Card
                    state.kernelData?.let { kernel ->
                        SpecCard(
                            icon = Icons.Rounded.Terminal,
                            title = "Kernel",
                            subtitle = kernel.release,
                            accentColor = Success,
                            details = listOf(
                                "System" to kernel.sysname,
                                "Version" to kernel.version.take(40),
                                "SELinux" to when (kernel.selinuxEnforcing) {
                                    true -> "Enforcing"
                                    false -> "Permissive"
                                    null -> "Unknown"
                                },
                                "Rooted" to if (kernel.rooted) "Yes ⚠️" else "No ✓"
                            )
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    // AI Card
                    state.aiData?.let { ai ->
                        SpecCard(
                            icon = Icons.Rounded.Psychology,
                            title = "AI Accelerator",
                            subtitle = ai.acceleratorType,
                            accentColor = TierTitan,
                            details = listOfNotNull(
                                ai.acceleratorName?.let { "Device" to it }
                            )
                        )
                        Spacer(Modifier.height(12.dp))
                    }

                    Spacer(Modifier.height(80.dp)) // bottom nav clearance
                }
            }
        }
    }
}

// ═══════════════════════════════════════════
// Dashboard Sub-Components
// ═══════════════════════════════════════════

@Composable
private fun DashboardHeader(state: DashboardUiState) {
    val deviceName = state.kernelData?.nodename ?: "PhoneScope"
    val kernelArch = state.kernelData?.machine ?: ""

    Column {
        Text(
            text = deviceName,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = PhoneScopeTheme.colors.textPrimary
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = buildString {
                append("Kernel ${state.kernelData?.release ?: "—"}")
                if (kernelArch.isNotEmpty()) append(" · $kernelArch")
            },
            style = MaterialTheme.typography.bodySmall,
            color = PhoneScopeTheme.colors.textTertiary
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = PrimaryCyan,
        letterSpacing = 1.sp
    )
}

@Composable
private fun VitalsGrid(state: DashboardUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HardwareMeter(
            label = "CPU",
            value = state.cpuUsagePercent,
            valueText = "${state.cpuUsagePercent.toInt()}%",
            color = PrimaryCyan
        )
        HardwareMeter(
            label = "RAM",
            value = state.memUsagePercent,
            valueText = "${state.memUsagePercent.toInt()}%",
            color = SecondaryAmber
        )
    }

    Spacer(Modifier.height(12.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        HardwareMeter(
            label = "GPU",
            value = state.gpuUsagePercent,
            valueText = "${state.gpuUsagePercent.toInt()}%",
            color = Success
        )
        HardwareMeter(
            label = "TEMP",
            value = (state.maxThermalTemp).coerceIn(0f, 100f),
            valueText = "${"%.1f".format(state.maxThermalTemp)}°C",
            color = when {
                state.maxThermalTemp > 70 -> Critical
                state.maxThermalTemp > 50 -> Warning
                else -> ThermalCool
            }
        )
    }
}

@Composable
private fun SpecCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    accentColor: Color,
    details: List<Pair<String, String>>
) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        SurfaceVariant,
                        Surface
                    )
                )
            )
            .border(
                width = 1.dp,
                color = PanelGlassBorder,
                shape = shape
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = PhoneScopeTheme.colors.textPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = PhoneScopeTheme.colors.textSecondary,
                    maxLines = 1
                )
            }
        }
        if (details.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))
            details.forEach { (key, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = key,
                        style = MaterialTheme.typography.bodySmall,
                        color = PhoneScopeTheme.colors.textTertiary
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = PhoneScopeTheme.colors.textPrimary
                    )
                }
            }
        }
    }
}
