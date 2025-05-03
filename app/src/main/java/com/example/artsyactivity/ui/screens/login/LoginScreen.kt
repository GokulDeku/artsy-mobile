@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.screens.login

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artsyactivity.utils.ArrowBackIcon
import com.example.artsyactivity.utils.CommonOutlinedTextField
import com.example.artsyactivity.utils.TextFieldType


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

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
                    ArrowBackIcon(onClick = onBackClick)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                textFieldType = TextFieldType.GENERAL,
                label = "Email",
                data = email,
                onValueChange = {
                    email = it
                },
                isValid = {
                    !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                }
            )

            CommonOutlinedTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                textFieldType = TextFieldType.PASSWORD,
                label = "Password",
                data = password,
                onValueChange = {
                    password = it
                },
                isValid = {
                    true
                }
            )

            Button(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                onClick = {}
            ) {
                Text(text = "Login")
            }

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Email or password is incorrect. Try again",
                color = MaterialTheme.colorScheme.error
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = buildAnnotatedString {

                    append("Don't have an Account yet? ")

                    append(
                        AnnotatedString.fromHtml(
                            htmlString = "<a href=''> Register </a>",
                            linkStyles = TextLinkStyles(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            ),
                            linkInteractionListener = remember {
                                object : LinkInteractionListener {
                                    override fun onClick(link: LinkAnnotation) {
                                        onRegisterClick()
                                    }
                                }
                            }
                        )
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(
        onBackClick = {},
        onRegisterClick = {}
    )
}
