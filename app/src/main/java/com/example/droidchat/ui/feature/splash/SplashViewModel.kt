package com.example.droidchat.ui.feature.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.model.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _authenticationState = MutableSharedFlow<AuthenticationState>(replay = 1)
    val authenticationState = _authenticationState.asSharedFlow()

    var showErrorDialogState by mutableStateOf(false)
        private set

    fun checkSession() {
        dismissErrorDialog()
        viewModelScope.launch {
            val accessToken = authRepository.getAccessToken()

            if (accessToken.isNullOrBlank()) {
                _authenticationState.emit(AuthenticationState.UserNotAuthenticated)
                return@launch
            }

            authRepository.authenticate().fold(
                onSuccess = {
                    _authenticationState.emit(AuthenticationState.UserAuthenticated)
                },
                onFailure = {
                    if (it is NetworkException.ApiException && it.statusCode == 401) {
                        authRepository.clearAccessToken()
                        _authenticationState.emit(AuthenticationState.UserNotAuthenticated)
                    } else {
                        showErrorDialogState = true
                    }
                }
            )
        }
    }

    fun dismissErrorDialog() {
        showErrorDialogState = false
    }

    sealed interface AuthenticationState {
        data object UserAuthenticated : AuthenticationState
        data object UserNotAuthenticated : AuthenticationState
    }
}