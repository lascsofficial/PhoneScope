package com.phonescope.inspector.ui.monitor

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.phonescope.inspector.ui.theme.*

@Composable
fun MonitorScreen(
    modifier: Modifier = Modifier,
    viewModel: MonitorViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PhoneScopeTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // ─── Header ───
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.Monitor, null, Modifier.size(24.dp), tint = SecondaryAmber)
            Spacer(Modifier.width(10.dp))
            Text(
                "Live Monitor",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = PhoneScopeTheme.colors.textPrimary
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(
            "Real-time telemetry • 1 Hz sampling",
            style = MaterialTheme.typography.bodySmall,
            color = PhoneScopeTheme.colors.textTertiary
        )

        Spacer(Modifier.height(24.dp))

        // ─── Memory Panel ───
        MonitorCard(
            title = "Memory Usage",
            valueText = "${state.memUsedMb} / ${state.memTotalMb} MB",
            percent = state.memUsedPercent,
            accentColor = SecondaryAmber,
            history = state.memHistory
        )

        Spacer(Modifier.height(16.dp))

        // ─── Thermal Panel ───
        MonitorCard(
            title = "Temperature",
            valueText = "${"%.1f".format(state.maxTemp)}°C${if (state.tempLabel.isNotEmpty()) " (${state.tempLabel})" else ""}",
            percent = state.maxTemp.coerceIn(0f, 100f),
            accentColor = when {
                state.maxTemp > 70 -> Critical
                state.maxTemp > 50 -> Warning
                else -> ThermalCool
            },
            history = state.tempHistory,
            maxValue = 100f
        )

        Spacer(Modifier.height(80.dp))
    }
}

// ═══════════════════════════════════════════
// Sub-components
// ═══════════════════════════════════════════

@Composable
private fun MonitorCard(
    title: String,
    valueText: String,
    percent: Float,
    accentColor: Color,
    history: List<Float>,
    maxValue: Float = 100f
) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Brush.verticalGradient(listOf(SurfaceVariant, Surface)))
            .border(1.dp, PanelGlassBorder, shape)
            .padding(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = PhoneScopeTheme.colors.textSecondary)
            Text(
                "${"%.0f".format(percent)}%",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = accentColor
            )
        }
        Spacer(Modifier.height(4.dp))
        Text(valueText, style = MaterialTheme.typography.bodySmall, color = PhoneScopeTheme.colors.textTertiary)

        Spacer(Modifier.height(12.dp))

        // Sparkline chart
        if (history.isNotEmpty()) {
            Sparkline(
                data = history,
                maxValue = maxValue,
                lineColor = accentColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            )
        }
    }
}

/**
 * A minimal, smooth sparkline chart rendered via Canvas.
 */
@Composable
private fun Sparkline(
    data: List<Float>,
    maxValue: Float,
    lineColor: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        if (data.size < 2) return@Canvas
        val w = size.width
        val h = size.height
        val step = w / (data.size - 1).coerceAtLeast(1)

        val path = Path()
        data.forEachIndexed { index, value ->
            val x = index * step
            val y = h - (value / maxValue).coerceIn(0f, 1f) * h
            if (index == 0) path.moveTo(x, y)
            else path.lineTo(x, y)
        }

        // Glow fill
        val fillPath = Path().apply {
            addPath(path)
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(
            fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(lineColor.copy(alpha = 0.25f), Color.Transparent)
            )
        )

        // Line stroke
        drawPath(
            path,
            color = lineColor,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}
