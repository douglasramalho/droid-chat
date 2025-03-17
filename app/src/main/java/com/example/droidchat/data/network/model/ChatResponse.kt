package com.example.droidchat.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedChatResponse(
    @SerialName("conversations")
    val chats: List<ChatResponse>,
    val hasMore: Boolean,
    val total: Int,
)

@Serializable
data class ChatResponse(
    val id: Int,
    val lastMessage: String?,
    val members: List<UserResponse>,
    val unreadCount: Int,
    val createdAt: Long,
    val updatedAt: Long,
)

data class PaginationParams(
    val offset: String,
    val limit: String,
)