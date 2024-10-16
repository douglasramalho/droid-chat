package com.example.droidchat.ui.validator

object EmailValidator {
    private const val EMAIL_REGEX = "^[A-Za-z](.*)([@])(.+)(\\.)(.+)"

    fun isValid(value: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(value)
    }
}