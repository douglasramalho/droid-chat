package com.example.droidchat.ui.feature.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.R
import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.model.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    var formState by mutableStateOf(SignInFormState())
        private set

    private val _signInActionFlow = MutableSharedFlow<SignInAction>()
    val signInActionFlow = _signInActionFlow.asSharedFlow()

    fun onFormEvent(event: SignInFormEvent) {
        when (event) {
            is SignInFormEvent.EmailChanged -> {
                formState = formState.copy(email = event.email, emailError = null)
            }

            is SignInFormEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password, passwordError = null)
            }

            SignInFormEvent.Submit -> {
                doSignIn()
            }
        }
    }

    private fun doSignIn() {
        var isFormValid = true
        if (formState.email.isBlank()) {
            formState = formState.copy(emailError = R.string.error_message_email_invalid)
            isFormValid = false
        }

        if (formState.password.isBlank()) {
            formState = formState.copy(passwordError = R.string.error_message_password_invalid)
            isFormValid = false
        }

        if (isFormValid) {
            formState = formState.copy(isLoading = true)
            viewModelScope.launch {
                authRepository.signIn(
                    username = formState.email,
                    password = formState.password,
                ).fold(
                    onSuccess = {
                        formState = formState.copy(isLoading = false)

                        _signInActionFlow.emit(SignInAction.Success)
                    },
                    onFailure = {
                        formState = formState.copy(isLoading = false)

                        val error = if (it is NetworkException.ApiException && it.statusCode == 401) {
                            SignInAction.Error.UnauthorizedError
                        } else {
                            SignInAction.Error.GenericError
                        }

                        _signInActionFlow.emit(error)
                    }
                )
            }
        }
    }

    sealed interface SignInAction {
        data object Success : SignInAction
        sealed interface Error : SignInAction {
            data object GenericError : Error
            data object UnauthorizedError : Error
        }
    }
}