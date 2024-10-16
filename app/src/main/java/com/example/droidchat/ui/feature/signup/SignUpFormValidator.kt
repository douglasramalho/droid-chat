package com.example.droidchat.ui.feature.signup

import com.example.droidchat.R
import com.example.droidchat.ui.validator.EmailValidator
import com.example.droidchat.ui.validator.FormValidator
import com.example.droidchat.ui.validator.PasswordValidator

class SignUpFormValidator : FormValidator<SignUpFormState> {

    override fun validate(formState: SignUpFormState): SignUpFormState {
        val isFirstNameValid = formState.firstName.isNotEmpty()
        val isLastNameValid = formState.lastName.isNotEmpty()
        val isEmailValid = EmailValidator.isValid(formState.email)
        val isPasswordValid = PasswordValidator.isValid(formState.password)
        val isPasswordConfirmationValid = PasswordValidator.isValid(formState.passwordConfirmation)
                && formState.passwordConfirmation == formState.password

        val hasError = listOf(
            isFirstNameValid,
            isLastNameValid,
            isEmailValid,
            isPasswordValid,
            isPasswordConfirmationValid,
        ).any { !it }

        return formState.copy(
            firstNameError = if (!isFirstNameValid) R.string.error_message_field_blank else null,
            lastNameError = if (!isLastNameValid) R.string.error_message_field_blank else null,
            emailError = if (!isEmailValid) R.string.error_message_email_invalid else null,
            passwordError = if (!isPasswordValid) R.string.error_message_password_invalid else null,
            passwordConfirmationError = if (!isPasswordConfirmationValid) R.string.error_message_password_confirmation_invalid else null,
            hasError = hasError,
        )
    }
}