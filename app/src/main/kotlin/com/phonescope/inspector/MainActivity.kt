package com.phonescope.inspector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.phonescope.inspector.ui.navigation.PhoneScopeNavHost
import com.phonescope.inspector.ui.theme.PhoneScopeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhoneScopeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PhoneScopeTheme.colors.background
                ) {
                    PhoneScopeNavHost()
                }
            }
        }
    }
}
