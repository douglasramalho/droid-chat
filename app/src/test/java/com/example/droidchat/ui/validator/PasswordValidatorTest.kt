package com.example.droidchat.ui.validator

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordValidatorTest {

    @Test
    fun `return true for valid password`() {
        val password = "Password123"
        val result = PasswordValidator.isValid(password)
        assertTrue(result)
    }

    @Test
    fun `returns false when fewer than 8 characters`() {
        val password = "Pass123"
        val result = PasswordValidator.isValid(password)
        assertFalse(result)
    }


    @Test
    fun `returns false when missing digits`() {
        val password = "Password"
        val result = PasswordValidator.isValid(password)
        assertFalse(result)
    }

    @Test
    fun `returns false when missing letters`() {
        val password = "12345678"
        val result = PasswordValidator.isValid(password)
        assertFalse(result)
    }

}