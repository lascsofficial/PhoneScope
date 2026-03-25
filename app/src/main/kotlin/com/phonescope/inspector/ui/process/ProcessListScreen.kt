package com.phonescope.inspector.ui.process

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phonescope.inspector.data.jni.NativeEngine
import com.phonescope.inspector.domain.model.ProcessEntry
import com.phonescope.inspector.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@Composable
fun ProcessListScreen(modifier: Modifier = Modifier) {
    val json = remember { Json { ignoreUnknownKeys = true } }
    var processes by remember { mutableStateOf<List<ProcessEntry>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            processes = runCatching {
                json.decodeFromString<List<ProcessEntry>>(NativeEngine.nativeGetProcessList())
                    .sortedByDescending { it.vmRssKb }
            }.getOrDefault(emptyList())
            isLoading = false
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(PhoneScopeTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.Apps, null, Modifier.size(24.dp), tint = PrimaryCyan)
            Spacer(Modifier.width(10.dp))
            Text("Processes", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = PhoneScopeTheme.colors.textPrimary)
        }
        Spacer(Modifier.height(4.dp))
        Text("${processes.size} active processes • sorted by RAM", style = MaterialTheme.typography.bodySmall, color = PhoneScopeTheme.colors.textTertiary)

        Spacer(Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally), color = PrimaryCyan)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(processes) { proc ->
                    ProcessRow(proc)
                }
            }
        }
    }
}

@Composable
private fun ProcessRow(proc: ProcessEntry) {
    val shape = RoundedCornerShape(12.dp)
    Row(
        modifier = Modifier.fillMaxWidth().clip(shape)
            .background(Brush.horizontalGradient(listOf(SurfaceVariant, Surface)))
            .border(1.dp, PanelGlassBorder, shape)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(proc.name, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = PhoneScopeTheme.colors.textPrimary, maxLines = 1)
            Text("PID ${proc.pid} • ${proc.threads} threads • ${proc.state}", style = MaterialTheme.typography.labelSmall, color = PhoneScopeTheme.colors.textTertiary)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text("${proc.vmRssKb / 1024} MB", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SecondaryAmber)
        }
    }
}
