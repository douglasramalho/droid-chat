package com.example.droidchat.ui.feature.signup

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.R
import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.model.CreateAccount
import com.example.droidchat.model.NetworkException
import com.example.droidchat.ui.validator.FormValidator
import com.example.droidchat.util.image.ImageCompressor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val formValidator: FormValidator<SignUpFormState>,
    private val authRepository: AuthRepository,
    private val imageCompressor: ImageCompressor,
) : ViewModel() {

    private val _formState = MutableStateFlow(SignUpFormState())
    val formState = _formState.asStateFlow()

    fun onFormEvent(event: SignUpFormEvent) {
        when (event) {
            is SignUpFormEvent.ProfilePhotoUriChanged -> {
                _formState.value = _formState.value.copy(profilePictureUri = event.uri)
                event.uri?.let {
                    compressImageAndUpdateState(it)
                }
            }
            is SignUpFormEvent.FirstNameChanged -> {
                _formState.value = _formState.value.copy(firstName = event.firstName)
            }
            is SignUpFormEvent.LastNameChanged -> {
                _formState.value = _formState.value.copy(lastName = event.lastName)
            }
            is SignUpFormEvent.EmailChanged -> {
                _formState.value = _formState.value.copy(email = event.email)
            }
            is SignUpFormEvent.PasswordChanged -> {
                _formState.value = _formState.value.copy(password = event.password)
                updatePasswordExtraText()
            }
            is SignUpFormEvent.PasswordConfirmationChanged -> {
                _formState.value = _formState.value.copy(passwordConfirmation = event.passwordConfirmation)
                updatePasswordExtraText()
            }
            SignUpFormEvent.OpenProfilePictureOptionsModalBottomSheet -> {
                _formState.value = _formState.value.copy(isProfilePictureModalBottomSheetOpen = true)
            }
            SignUpFormEvent.CloseProfilePictureOptionsModalBottomSheet -> {
                _formState.value = _formState.value.copy(isProfilePictureModalBottomSheetOpen = false)
            }
            SignUpFormEvent.Submit -> {
                doSignUp()
            }
        }
    }

    private fun compressImageAndUpdateState(uri: Uri) {
        viewModelScope.launch {
            try {
                _formState.value = _formState.value.copy(isCompressingImage = true)
                val compressedFile = imageCompressor.compressAndResizeImage(uri)
                _formState.value = _formState.value.copy(profilePictureUri = compressedFile.toUri())
            } catch (e: Exception) {
                // Log error
            } finally {
                _formState.value = _formState.value.copy(isCompressingImage = false)
            }
        }
    }

    private fun updatePasswordExtraText() {
        _formState.value = _formState.value.copy(
            passwordExtraText = if (_formState.value.password.isNotEmpty()
                && _formState.value.password == _formState.value.passwordConfirmation) {
                R.string.feature_sign_up_passwords_match
            } else null
        )
    }

    private fun doSignUp() {
        if (isValidForm()) {
            _formState.value = _formState.value.copy(isLoading = true)
            viewModelScope.launch {
                var profilePictureId: Int? = null
                var errorWhenUploadingProfilePicture = false

                _formState.value.profilePictureUri?.path?.let { path ->
                    authRepository.uploadProfilePicture(path).fold(
                        onSuccess = { image ->
                            profilePictureId = image.id
                        },
                        onFailure = {
                            _formState.value = _formState.value.copy(
                                isLoading = false,
                                profilePictureUri = null,
                                apiErrorMessageResId = R.string.error_message_profile_picture_uploading_failed
                            )
                            errorWhenUploadingProfilePicture = true
                        }
                    )
                }

                if (errorWhenUploadingProfilePicture) {
                    return@launch
                }

                authRepository.signUp(
                    createAccount = CreateAccount(
                        username = _formState.value.email,
                        password = _formState.value.password,
                        firstName = _formState.value.firstName,
                        lastName = _formState.value.lastName,
                        profilePictureId = profilePictureId,
                    )
                ).fold(
                    onSuccess = {
                        _formState.value = _formState.value.copy(
                            isLoading = false,
                            isSignedUp = true,
                        )
                    },
                    onFailure = {
                        _formState.value = _formState.value.copy(
                            isLoading = false,
                            apiErrorMessageResId = if (it is NetworkException.ApiException) {
                                when (it.statusCode) {
                                    400 -> R.string.error_message_api_form_validation_failed
                                    409 -> R.string.error_message_user_with_username_already_exists
                                    else -> R.string.common_generic_error_message
                                }
                            } else R.string.common_generic_error_message
                        )
                    }
                )
            }
        }
    }

    private fun isValidForm(): Boolean {
        return !formValidator.validate(_formState.value).also {
            _formState.value = it
        }.hasError
    }

    fun successMessageShown() {
        _formState.value = _formState.value.copy(isSignedUp = false)
    }

    fun errorMessageShown() {
        _formState.value = _formState.value.copy(apiErrorMessageResId = null)
    }
}