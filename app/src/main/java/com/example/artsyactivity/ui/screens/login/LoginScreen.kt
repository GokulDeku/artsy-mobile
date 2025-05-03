@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artsyactivity.utils.ArrowBackIcon


@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = "Login",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontSize = 22.sp
                        )
                    )
                },
                navigationIcon = {
                    ArrowBackIcon
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen()
}
