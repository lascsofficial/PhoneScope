package com.phonescope.inspector.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phonescope.inspector.ui.theme.*

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
) {
    var continuousSampling by remember { mutableStateOf(true) }
    var samplingRate by remember { mutableStateOf(1f) }
    var showAllProcesses by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PhoneScopeTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // ─── Header ───
        Text(
            text = "Settings",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = PhoneScopeTheme.colors.textPrimary
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Configure inspection behavior",
            style = MaterialTheme.typography.bodySmall,
            color = PhoneScopeTheme.colors.textTertiary
        )

        Spacer(Modifier.height(24.dp))

        // ─── Monitor Section ───
        SectionLabel("MONITOR")
        Spacer(Modifier.height(8.dp))

        SettingsCard {
            ToggleRow(
                icon = Icons.Rounded.PlayCircle,
                title = "Continuous Sampling",
                subtitle = "Keep polling hardware metrics in background",
                checked = continuousSampling,
                onCheckedChange = { continuousSampling = it }
            )

            HorizontalDivider(color = PanelGlassBorder, modifier = Modifier.padding(vertical = 8.dp))

            Column(Modifier.padding(horizontal = 4.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Speed, null, Modifier.size(20.dp), tint = PrimaryCyan)
                        Spacer(Modifier.width(12.dp))
                        Text("Sample Rate", fontSize = 14.sp, color = PhoneScopeTheme.colors.textPrimary)
                    }
                    Text(
                        "${samplingRate.toInt()}s",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryCyan,
                        fontSize = 14.sp
                    )
                }
                Slider(
                    value = samplingRate,
                    onValueChange = { samplingRate = it },
                    valueRange = 1f..10f,
                    steps = 8,
                    colors = SliderDefaults.colors(
                        thumbColor = PrimaryCyan,
                        activeTrackColor = PrimaryCyan,
                        inactiveTrackColor = SurfaceElevated
                    )
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // ─── Scan Section ───
        SectionLabel("SCAN")
        Spacer(Modifier.height(8.dp))

        SettingsCard {
            ToggleRow(
                icon = Icons.Rounded.Visibility,
                title = "Show All Processes",
                subtitle = "Include system and kernel threads in process list",
                checked = showAllProcesses,
                onCheckedChange = { showAllProcesses = it }
            )
        }

        Spacer(Modifier.height(20.dp))

        // ─── About Section ───
        SectionLabel("ABOUT")
        Spacer(Modifier.height(8.dp))

        SettingsCard {
            InfoRow(
                icon = Icons.Rounded.Info,
                title = "Version",
                value = "1.0.0-alpha"
            )
            HorizontalDivider(color = PanelGlassBorder, modifier = Modifier.padding(vertical = 8.dp))
            InfoRow(
                icon = Icons.Rounded.Code,
                title = "Engine",
                value = "C++20 / NDK r27"
            )
            HorizontalDivider(color = PanelGlassBorder, modifier = Modifier.padding(vertical = 8.dp))
            InfoRow(
                icon = Icons.Rounded.Architecture,
                title = "Architecture",
                value = "MVVM + Clean"
            )
            HorizontalDivider(color = PanelGlassBorder, modifier = Modifier.padding(vertical = 8.dp))
            InfoRow(
                icon = Icons.Rounded.Person,
                title = "Developer",
                value = "lascsofficial"
            )
            HorizontalDivider(color = PanelGlassBorder, modifier = Modifier.padding(vertical = 8.dp))
            InfoRow(
                icon = Icons.Rounded.Link,
                title = "Source",
                value = "github.com/lascsofficial/PhoneScope"
            )
        }

        Spacer(Modifier.height(80.dp))
    }
}

// ═══════════════════════════════════════════
// Sub-components
// ═══════════════════════════════════════════

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = PrimaryCyan,
        letterSpacing = 1.5.sp
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.verticalGradient(listOf(SurfaceVariant, Surface)))
            .border(1.dp, PanelGlassBorder, shape)
            .padding(16.dp),
        content = content
    )
}

@Composable
private fun ToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(icon, null, Modifier.size(20.dp), tint = PrimaryCyan)
            Spacer(Modifier.width(12.dp))
            Column {
                Text(title, fontSize = 14.sp, color = PhoneScopeTheme.colors.textPrimary)
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = PhoneScopeTheme.colors.textTertiary)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Black,
                checkedTrackColor = PrimaryCyan,
                uncheckedThumbColor = PhoneScopeTheme.colors.textTertiary,
                uncheckedTrackColor = SurfaceElevated
            )
        )
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, Modifier.size(20.dp), tint = PrimaryCyan)
            Spacer(Modifier.width(12.dp))
            Text(title, fontSize = 14.sp, color = PhoneScopeTheme.colors.textPrimary)
        }
        Text(
            value,
            style = MaterialTheme.typography.bodySmall,
            color = PhoneScopeTheme.colors.textSecondary,
            fontWeight = FontWeight.Medium
        )
    }
}
