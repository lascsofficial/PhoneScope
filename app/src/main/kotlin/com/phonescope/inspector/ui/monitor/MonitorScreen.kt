package com.phonescope.inspector.ui.monitor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Monitor
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phonescope.inspector.ui.theme.PhoneScopeTheme

@Composable
fun MonitorScreen(
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
                imageVector = Icons.Rounded.Monitor,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = PhoneScopeTheme.colors.secondaryAmber,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Live Monitor",
                style = MaterialTheme.typography.headlineMedium,
                color = PhoneScopeTheme.colors.textPrimary,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Real-time flight recorder — Coming in Sprint S13",
                style = MaterialTheme.typography.bodyMedium,
                color = PhoneScopeTheme.colors.textTertiary,
            )
        }
    }
}
