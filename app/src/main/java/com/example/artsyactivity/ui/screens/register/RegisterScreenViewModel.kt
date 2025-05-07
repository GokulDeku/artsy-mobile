package com.example.artsyactivity.ui.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artsyactivity.ArtsyApplication
import com.example.artsyactivity.data.network.models.request.RegisterData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject

class RegisterScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun updateEmailError(message: String) {
        _uiState.update { it.copy(emailError = message) }
    }

    fun clearEmailError() {
        _uiState.update { it.copy(emailError = "") }
    }

    fun setLoading(value: Boolean) {
        _uiState.update { it.copy(isLoading = value) }
    }

    fun register(name: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            setLoading(true)
            clearEmailError()

            try {
                val response = ArtsyApplication.providesAuthService().register(
                    RegisterData(name, email, password)
                )

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.d("REGISTER_SUCCESS", "Body: $responseBody")
                        onSuccess()
                    } else {
                        updateEmailError("Unexpected empty response.")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = JSONObject(errorBody ?: "{}").optString("message")
                    updateEmailError(errorMessage.ifBlank { "Something went wrong." })
                }
            } catch (e: Exception) {
                e.printStackTrace()
                updateEmailError("Check your internet connection.")
            }

            setLoading(false)
        }
    }


    data class UiState(
        val emailError: String = "",
        val isLoading: Boolean = false
    )

    data class ApiError(
        val message: String,
        val statusCode: Int
    )
}
