package com.phonescope.inspector.ui.scan

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import com.phonescope.inspector.ui.theme.*

@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
    viewModel: ScanViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PhoneScopeTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // ─── Header ───
            Text(
                text = "Device Scan",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = PhoneScopeTheme.colors.textPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Deep hardware inspection across all modules",
                style = MaterialTheme.typography.bodySmall,
                color = PhoneScopeTheme.colors.textTertiary
            )

            Spacer(Modifier.height(20.dp))

            // ─── Scan Button / Progress ───
            when (state.phase) {
                ScanPhase.Idle -> {
                    Button(
                        onClick = { viewModel.startScan() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryCyan,
                            contentColor = Black
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Rounded.PlayArrow, null, Modifier.size(24.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Start Full Scan", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
                ScanPhase.Scanning -> {
                    ScanProgressCard(state)
                }
                ScanPhase.Complete -> {
                    // Results summary
                    ScanCompleteCard()
                    Spacer(Modifier.height(16.dp))
                    // Re-scan button
                    OutlinedButton(
                        onClick = { viewModel.startScan() },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryCyan),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Rounded.Refresh, null, Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Re-Scan", fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ─── Result Cards ───
            if (state.phase == ScanPhase.Complete) {
                state.cpuData?.let { cpu ->
                    ScanResultCard(
                        icon = Icons.Rounded.Memory,
                        title = "CPU",
                        accentColor = PrimaryCyan,
                        items = listOf(
                            "Cores" to "${cpu.processors.size}",
                            "Governor" to (cpu.processors.firstOrNull()?.governor ?: "—"),
                            "Vulnerabilities" to "${cpu.vulnerabilities.size} checked"
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                }

                state.memoryData?.let { mem ->
                    val totalMb = (mem.meminfo["MemTotal"] ?: 0L) / 1024
                    ScanResultCard(
                        icon = Icons.Rounded.Storage,
                        title = "Memory",
                        accentColor = SecondaryAmber,
                        items = listOf(
                            "Total RAM" to "${totalMb} MB",
                            "Swap" to "${(mem.meminfo["SwapTotal"] ?: 0L) / 1024} MB"
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                }

                state.gpuData?.let { gpu ->
                    ScanResultCard(
                        icon = Icons.Rounded.Videocam,
                        title = "GPU",
                        accentColor = Success,
                        items = listOf(
                            "Vendor" to gpu.vendor,
                            "Model" to gpu.model
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                }

                state.thermalData?.let { thermal ->
                    ScanResultCard(
                        icon = Icons.Rounded.Thermostat,
                        title = "Thermal",
                        accentColor = Warning,
                        items = listOf(
                            "Zones" to "${thermal.zones.size}",
                            "Cooling Devices" to "${thermal.coolingDevices.size}"
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                }

                state.kernelData?.let { kernel ->
                    ScanResultCard(
                        icon = Icons.Rounded.Terminal,
                        title = "Kernel",
                        accentColor = TierFlagship,
                        items = listOf(
                            "Release" to kernel.release,
                            "SELinux" to when (kernel.selinuxEnforcing) {
                                true -> "Enforcing ✓"
                                false -> "Permissive ⚠️"
                                null -> "Unknown"
                            },
                            "Root" to if (kernel.rooted) "Detected ⚠️" else "Not Found ✓"
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                }

                state.storageData?.let { storage ->
                    ScanResultCard(
                        icon = Icons.Rounded.Speed,
                        title = "Storage Benchmark",
                        accentColor = TierTitan,
                        items = listOf(
                            "Seq. Write" to "${"%.1f".format(storage.writeSpeed)} MB/s",
                            "Seq. Read" to "${"%.1f".format(storage.readSpeed)} MB/s",
                            "Tier" to storage.tier
                        )
                    )
                    Spacer(Modifier.height(12.dp))
                }

                state.aiData?.let { ai ->
                    ScanResultCard(
                        icon = Icons.Rounded.Psychology,
                        title = "AI Accelerator",
                        accentColor = CoreBig0,
                        items = listOf("Type" to ai.acceleratorType)
                    )
                    Spacer(Modifier.height(12.dp))
                }

                if (state.processCount > 0) {
                    ScanResultCard(
                        icon = Icons.Rounded.Apps,
                        title = "Processes",
                        accentColor = CoreLittle0,
                        items = listOf("Active" to "${state.processCount}")
                    )
                }
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}

// ═══════════════════════════════════════════
// Sub-components
// ═══════════════════════════════════════════

@Composable
private fun ScanProgressCard(state: ScanUiState) {
    val animProgress by animateFloatAsState(
        targetValue = state.progress,
        animationSpec = tween(400), label = "scanProg"
    )
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(SurfaceVariant)
            .border(1.dp, PanelGlassBorder, shape)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Scanning ${state.currentModule}…",
            fontWeight = FontWeight.SemiBold,
            color = PrimaryCyan,
            fontSize = 15.sp
        )
        Spacer(Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { animProgress },
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
            color = PrimaryCyan,
            trackColor = SurfaceElevated
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "${(animProgress * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall,
            color = PhoneScopeTheme.colors.textTertiary
        )
    }
}

@Composable
private fun ScanCompleteCard() {
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.horizontalGradient(listOf(PrimaryCyanGlow, Color.Transparent)))
            .border(1.dp, PrimaryCyanGlow, shape)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Rounded.CheckCircle, null, Modifier.size(28.dp), tint = Success)
        Spacer(Modifier.width(12.dp))
        Column {
            Text("Scan Complete", fontWeight = FontWeight.Bold, color = PhoneScopeTheme.colors.textPrimary)
            Text("All modules inspected successfully", style = MaterialTheme.typography.bodySmall, color = PhoneScopeTheme.colors.textSecondary)
        }
    }
}

@Composable
private fun ScanResultCard(
    icon: ImageVector,
    title: String,
    accentColor: Color,
    items: List<Pair<String, String>>
) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.verticalGradient(listOf(SurfaceVariant, Surface)))
            .border(1.dp, PanelGlassBorder, shape)
            .padding(16.dp)
            .animateContentSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, Modifier.size(24.dp), tint = accentColor)
            Spacer(Modifier.width(10.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = PhoneScopeTheme.colors.textPrimary)
        }
        Spacer(Modifier.height(10.dp))
        items.forEach { (key, value) ->
            Row(
                Modifier.fillMaxWidth().padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(key, style = MaterialTheme.typography.bodySmall, color = PhoneScopeTheme.colors.textTertiary)
                Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium, color = PhoneScopeTheme.colors.textPrimary)
            }
        }
    }
}
