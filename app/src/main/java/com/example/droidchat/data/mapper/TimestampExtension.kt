package com.example.droidchat.data.mapper

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toTimestamp(): String {
    val messageDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )

    val now = LocalDateTime.now()

    return if (messageDateTime.toLocalDate() == now.toLocalDate()) {
        messageDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    } else {
        messageDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }
}