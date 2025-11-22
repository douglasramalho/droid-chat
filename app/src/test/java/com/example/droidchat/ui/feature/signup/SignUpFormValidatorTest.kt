package com.example.droidchat.ui.feature.signup

import com.example.droidchat.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SignUpFormValidatorTest {

    private lateinit var validator: SignUpFormValidator

    @Before
    fun setUp() {
        validator = SignUpFormValidator()
    }

    @Test
    fun `returns form state with error when all fields are invalid`() {
        // Arrange
        val formState = SignUpFormState(
            firstName = "",
            lastName = "",
            email = "",
            password = "",
            passwordConfirmation = ""
        )

        // Act
        val result = validator.validate(formState)

        // Assert
        assertEquals(
            formState.copy(
                firstNameError = R.string.error_message_field_blank,
                lastNameError = R.string.error_message_field_blank,
                emailError = R.string.error_message_email_invalid,
                passwordError = R.string.error_message_password_invalid,
                passwordConfirmationError = R.string.error_message_password_confirmation_invalid,
                hasError = true,
            ),
            result
        )
    }

    @Test
    fun `returns form state with error when only one field is invalid`() {
        // Arrange
        val formState = SignUpFormState(
            firstName = "",
            lastName = "Last",
            email = "email@email.com",
            password = "12345678q",
            passwordConfirmation = "12345678q"
        )

        // Act
        val result = validator.validate(formState)

        // Assert
        assertEquals(
            formState.copy(
                firstNameError = R.string.error_message_field_blank,
                hasError = true,
            ),
            result
        )
    }

    @Test
    fun `returns form state without error when all fields are valid`() {
        // Arrange
        val formState = SignUpFormState(
            firstName = "First",
            lastName = "Last",
            email = "email@email.com",
            password = "12345678q",
            passwordConfirmation = "12345678q"
        )

        // Act
        val result = validator.validate(formState)

        // Assert
        assertEquals(
            formState.copy(
                hasError = false,
            ),
            result
        )
    }

}