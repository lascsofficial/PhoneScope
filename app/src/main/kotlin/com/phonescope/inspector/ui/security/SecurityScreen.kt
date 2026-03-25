package com.phonescope.inspector.ui.security

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phonescope.inspector.data.jni.NativeEngine
import com.phonescope.inspector.domain.model.KernelData
import com.phonescope.inspector.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

@Composable
fun SecurityScreen(modifier: Modifier = Modifier) {
    val json = remember { Json { ignoreUnknownKeys = true; coerceInputValues = true } }
    var kernel by remember { mutableStateOf<KernelData?>(null) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            kernel = runCatching { json.decodeFromString<KernelData>(NativeEngine.nativeGetKernelInfo()) }.getOrNull()
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(PhoneScopeTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.Shield, null, Modifier.size(24.dp), tint = SecurityHardened)
            Spacer(Modifier.width(10.dp))
            Text("Security Audit", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = PhoneScopeTheme.colors.textPrimary)
        }
        Spacer(Modifier.height(4.dp))
        Text("Device security posture analysis", style = MaterialTheme.typography.bodySmall, color = PhoneScopeTheme.colors.textTertiary)

        Spacer(Modifier.height(20.dp))

        kernel?.let { k ->
            SecurityItem("SELinux", when (k.selinuxEnforcing) {
                true -> "Enforcing" to SecurityHardened
                false -> "Permissive" to SecurityAtRisk
                null -> "Unknown" to SecurityFair
            })
            Spacer(Modifier.height(10.dp))
            SecurityItem("Root Access", if (k.rooted) "Detected" to SecurityCritical else "Not Found" to SecurityHardened)
            Spacer(Modifier.height(10.dp))
            SecurityItem("Kernel", k.release to SecurityGood)
            Spacer(Modifier.height(10.dp))
            SecurityItem("Architecture", k.machine to SecurityGood)
        } ?: CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally), color = PrimaryCyan)

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun SecurityItem(label: String, data: Pair<String, Color>) {
    val (value, color) = data
    val shape = RoundedCornerShape(16.dp)
    Row(
        modifier = Modifier.fillMaxWidth().clip(shape)
            .background(Brush.horizontalGradient(listOf(color.copy(alpha = 0.08f), Color.Transparent)))
            .border(1.dp, color.copy(alpha = 0.3f), shape)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = PhoneScopeTheme.colors.textPrimary)
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = color)
    }
}
