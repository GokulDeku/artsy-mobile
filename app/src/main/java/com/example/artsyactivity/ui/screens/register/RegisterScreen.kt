@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.artsyactivity.ui.screens.register

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artsyactivity.utils.ArrowBackIcon
import com.example.artsyactivity.utils.CommonOutlinedTextField
import com.example.artsyactivity.utils.TextFieldType

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel = viewModel(),
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
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
                isInvalid = {
                    it.trim().isEmpty()
                }
            )

            CommonOutlinedTextField(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                textFieldType = TextFieldType.GENERAL,
                label = "Email",
                isError = uiState.emailError.isNotEmpty() || (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()),
                errorMessage = when {
                    uiState.emailError.isNotEmpty() -> uiState.emailError
                    email.isEmpty() -> "Email cannot be empty"
                    else -> "Invalid email format"
                },
                data = email,
                onValueChange = {
                    email = it
                },
                isInvalid = {
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
                isInvalid = {
                    it.trim().isEmpty()
                }
            )

            Button(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                onClick = {
                    val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    val isNameValid = name.isNotBlank()
                    val isPasswordValid = password.isNotBlank()

                    if (!isNameValid || !isEmailValid || !isPasswordValid) {
                        return@Button
                    }

                    viewModel.register(name, email, password) {
                        onLoginClick()
                    }
                }
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text(text = "Register")
                }
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
