package com.example.droidchat.data.mapper

import com.example.droidchat.data.network.model.PaginatedChatResponse
import com.example.droidchat.model.Chat
import com.example.droidchat.model.User
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun PaginatedChatResponse.asDomainModel(selfUserId: Int?): List<Chat> = this.chats.map { chatResponse ->
    Chat(
        id = chatResponse.id,
        lastMessage = chatResponse.lastMassage,
        members = chatResponse.members.map { userResponse ->
            User(
                id = userResponse.id,
                self = userResponse.id == selfUserId,
                firstName = userResponse.firstName,
                lastName = userResponse.lastName,
                profilePictureUrl = userResponse.profilePictureUrl,
                username = userResponse.username,
            )
        },
        unreadCount = chatResponse.unreadCount,
        timestamp = chatResponse.updatedAt.toTimestamp()
    )
}

private fun Long.toTimestamp(): String {
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