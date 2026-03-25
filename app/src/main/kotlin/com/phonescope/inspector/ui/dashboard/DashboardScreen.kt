package com.phonescope.inspector.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phonescope.inspector.ui.theme.PhoneScopeTheme

@Composable
fun DashboardScreen(
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
                imageVector = Icons.Rounded.Dashboard,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = PhoneScopeTheme.colors.primaryCyan,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "PhoneScope",
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                color = PhoneScopeTheme.colors.textPrimary,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Dashboard — Coming in Sprint S12",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = PhoneScopeTheme.colors.textTertiary,
            )
        }
    }
}
