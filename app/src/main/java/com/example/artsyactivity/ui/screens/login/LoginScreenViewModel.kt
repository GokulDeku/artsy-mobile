package com.example.artsyactivity.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artsyactivity.ArtsyApplication
import com.example.artsyactivity.data.network.models.request.LoginData
import com.example.artsyactivity.data.network.models.response.LoginResponse
import com.example.artsyactivity.network.ApiResult
import com.example.artsyactivity.network.safeApiCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    val authService = ArtsyApplication.providesAuthService()

    fun onLoginClick(email: String, password: String, onLoginSuccess: (LoginResponse) -> Unit) {
        viewModelScope.launch {
            updateShouldShowError(false)
            updateErrorMessage("")
            updateIsLoadingStatus(true)

            val result = safeApiCall {
                authService.login(
                    LoginData(
                        email = email,
                        password = password
                    )
                )
            }

            when (result) {
                is ApiResult.Success -> {
                    Log.d("VIJ", "In success block: ${result.data}")
                    updateIsLoadingStatus(false)
                    onLoginSuccess(result.data)
                }

                is ApiResult.Error -> {
                    updateShouldShowError(true)
                    updateIsLoadingStatus(false)
                    updateErrorMessage(result.error.message)
                }
            }
        }
    }

    fun updateIsLoadingStatus(isLoading: Boolean) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }
    }

    fun updateErrorMessage(message: String) {
        _uiState.update {
            it.copy(
                errorMessage = message
            )
        }
    }

    fun updateShouldShowError(shouldShowError: Boolean) {
        _uiState.update {
            it.copy(
                shouldShowError = shouldShowError
            )
        }
    }

    sealed interface UiAction {
        data object OnBackClicked : UiAction
        data class OnLoginClicked(val email: String, val password: String) : UiAction
        data object OnRegisterClicked : UiAction
    }

    data class UiState(
        val isLoading: Boolean = false,
        val shouldShowError: Boolean = false,
        val errorMessage: String = ""
    )

}