package com.phonescope.inspector.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val PhoneScopeShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),      // Inner elements
    large = RoundedCornerShape(16.dp),       // Cards
    extraLarge = RoundedCornerShape(24.dp),  // Bottom sheets
)
