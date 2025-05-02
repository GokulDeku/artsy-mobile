package com.example.artsyactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.artsyactivity.ui.screens.HomeScreen
import com.example.artsyactivity.ui.theme.ArtsyActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ArtsyActivityTheme {
                HomeScreen()
            }
        }
    }
}