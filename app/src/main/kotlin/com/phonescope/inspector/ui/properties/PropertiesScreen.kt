package com.phonescope.inspector.ui.properties

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.phonescope.inspector.data.jni.NativeEngine
import com.phonescope.inspector.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun PropertiesScreen(modifier: Modifier = Modifier) {
    val json = remember { Json { ignoreUnknownKeys = true } }
    var properties by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            properties = runCatching {
                val obj = json.decodeFromString<JsonObject>(NativeEngine.nativeGetAllSystemProps())
                obj.entries.map { it.key to it.value.jsonPrimitive.content }.sortedBy { it.first }
            }.getOrDefault(emptyList())
            isLoading = false
        }
    }

    val filtered = if (searchQuery.isBlank()) properties
                   else properties.filter { it.first.contains(searchQuery, ignoreCase = true) || it.second.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = modifier.fillMaxSize().background(PhoneScopeTheme.colors.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.List, null, Modifier.size(24.dp), tint = PrimaryCyan)
            Spacer(Modifier.width(10.dp))
            Text("System Properties", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = PhoneScopeTheme.colors.textPrimary)
        }
        Spacer(Modifier.height(4.dp))
        Text("${filtered.size} of ${properties.size} properties", style = MaterialTheme.typography.bodySmall, color = PhoneScopeTheme.colors.textTertiary)

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Filter properties…", color = PhoneScopeTheme.colors.textTertiary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryCyan, unfocusedBorderColor = PanelGlassBorder,
                cursorColor = PrimaryCyan, focusedTextColor = PhoneScopeTheme.colors.textPrimary,
                unfocusedTextColor = PhoneScopeTheme.colors.textPrimary
            )
        )

        Spacer(Modifier.height(12.dp))

        if (isLoading) {
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally), color = PrimaryCyan)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(filtered) { (key, value) ->
                    PropertyRow(key, value)
                }
            }
        }
    }
}

@Composable
private fun PropertyRow(key: String, value: String) {
    val shape = RoundedCornerShape(8.dp)
    Column(
        modifier = Modifier.fillMaxWidth().clip(shape)
            .background(SurfaceVariant).border(1.dp, PanelGlassBorder, shape)
            .padding(10.dp)
    ) {
        Text(key, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = PrimaryCyan, maxLines = 1)
        Text(value, fontSize = 12.sp, color = PhoneScopeTheme.colors.textSecondary, maxLines = 2)
    }
}
