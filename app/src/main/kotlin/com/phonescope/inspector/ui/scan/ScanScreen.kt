package com.phonescope.inspector.ui.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DocumentScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phonescope.inspector.ui.theme.PhoneScopeTheme

@Composable
fun ScanScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PhoneScopeTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Rounded.DocumentScanner,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = PhoneScopeTheme.colors.success,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Device Scan",
                style = MaterialTheme.typography.headlineMedium,
                color = PhoneScopeTheme.colors.textPrimary,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Full scan engine — Coming in Sprint S11",
                style = MaterialTheme.typography.bodyMedium,
                color = PhoneScopeTheme.colors.textTertiary,
            )
        }
    }
}
