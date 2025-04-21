package com.example.artsyactivity

import com.example.artsyactivity.ui.screens.HomeScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.artsyactivity.ui.theme.ArtsyActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtsyActivityTheme {
                HomeScreen()
            }
        }
    }
}