package com.example.artsyactivity.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TextFieldType {
    GENERAL,
    PASSWORD
}

@Composable
fun CommonOutlinedTextField(
    modifier: Modifier = Modifier,
    textFieldType: TextFieldType,
    data: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorMessage: String? = null,
    isValid: (String) -> Boolean
) {
    var value by rememberSaveable(data) { mutableStateOf(data) }
    var shouldShowError by rememberSaveable(isError) { mutableStateOf(isError) }

    var isFocused by rememberSaveable { mutableStateOf<Boolean?>(null) }

    val supportingText: @Composable (() -> Unit)? = {
        if (shouldShowError) {
            Box(modifier = Modifier.padding(vertical = 5.dp, horizontal = 6.dp)) {
                Text(
                    modifier = Modifier,
                    text = errorMessage.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp
                    )
                )
            }
        } else null
    }


    Column {
        OutlinedTextField(
            modifier = modifier.onFocusChanged {
                if (isFocused != null && !it.isFocused) {
                    shouldShowError = isValid(value)
                }

                if (it.isFocused) {
                    isFocused = it.isFocused
                }
            },
            value = value,
            onValueChange = {
                value = it
                onValueChange(it)
                if (shouldShowError) {
                    shouldShowError = isValid(value)
                }
            },
            label = {
                Text(
                    text = label,
                )
            },
            isError = shouldShowError,
            visualTransformation = when (textFieldType) {
                TextFieldType.GENERAL -> VisualTransformation.None
                TextFieldType.PASSWORD -> PasswordVisualTransformation()
            }
        )
        supportingText?.invoke()
    }
}


@Preview
@Composable
private fun PreviewCommonOutlinedTextField() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonOutlinedTextField(
            modifier = Modifier,
            textFieldType = TextFieldType.GENERAL,
            label = "Email",
            data = "",
            errorMessage = "Email is Invalid",
            onValueChange = {

            },
            isValid = {
                !it.contains("@")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CommonOutlinedTextField(
            textFieldType = TextFieldType.PASSWORD,
            label = "Password",
            data = "abcd@123",
            onValueChange = {},
            isValid = {
                true
            }
        )
    }
}