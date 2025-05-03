@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.screens.register

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
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = "Register",
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
                label = "Enter full name",
                data = name,
                errorMessage = "Full name cannot be empty",
                onValueChange = {
                    name = it
                },
                isValid = {
                    !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                }
            )

            CommonOutlinedTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                textFieldType = TextFieldType.GENERAL,
                label = "Email",
                errorMessage =
                    if (email.isEmpty())
                        "Email cannot be empty"
                    else
                        "Invalid email format",
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
                errorMessage = "Password cannot be empty",
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
                Text(text = "Register")
            }

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = buildAnnotatedString {

                    append("Already have an account? ")

                    append(
                        AnnotatedString.fromHtml(
                            htmlString = "<a href=''> Login </a>",
                            linkStyles = TextLinkStyles(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            ),
                            linkInteractionListener = remember {
                                object : LinkInteractionListener {
                                    override fun onClick(link: LinkAnnotation) {
                                        onLoginClick()
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
private fun PreviewRegisterScreen() {
    RegisterScreen(
        onBackClick = {},
        onLoginClick = {}
    )
}
