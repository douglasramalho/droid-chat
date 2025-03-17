package com.example.droidchat.data.mapper

import com.example.droidchat.data.network.model.PaginatedChatResponse
import com.example.droidchat.model.Chat
import com.example.droidchat.model.User

fun PaginatedChatResponse.asDomainModel(selfUserId: Int?): List<Chat> = this.chats.map { chatResponse ->
    Chat(
        id = chatResponse.id,
        lastMessage = chatResponse.lastMessage,
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