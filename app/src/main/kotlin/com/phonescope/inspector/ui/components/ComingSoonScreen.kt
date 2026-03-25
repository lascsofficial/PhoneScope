package com.phonescope.inspector.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.phonescope.inspector.ui.theme.PhoneScopeTheme
import com.phonescope.inspector.ui.theme.PrimaryCyan

@Composable
fun ComingSoonScreen(
    title: String,
    modifier: Modifier = Modifier
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
                imageVector = Icons.Rounded.Build,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = PrimaryCyan,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = PhoneScopeTheme.colors.textPrimary,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Advanced feature arriving in future update",
                style = MaterialTheme.typography.bodyMedium,
                color = PhoneScopeTheme.colors.textTertiary,
            )
        }
    }
}
