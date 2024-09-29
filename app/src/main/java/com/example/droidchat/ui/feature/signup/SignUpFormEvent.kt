package com.example.droidchat.ui.feature.signup

import android.net.Uri

sealed interface SignUpFormEvent {
    data class ProfilePhotoUriChanged(val uri: Uri?) : SignUpFormEvent
    data class FirstNameChanged(val firstName: String) : SignUpFormEvent
    data class LastNameChanged(val lastName: String) : SignUpFormEvent
    data class EmailChanged(val email: String) : SignUpFormEvent
    data class PasswordChanged(val password: String) : SignUpFormEvent
    data class PasswordConfirmationChanged(val passwordConfirmation: String) : SignUpFormEvent
    data object OpenProfilePictureOptionsModalBottomSheet : SignUpFormEvent
    data object CloseProfilePictureOptionsModalBottomSheet : SignUpFormEvent
    data object Submit : SignUpFormEvent
}