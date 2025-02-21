package com.example.droidchat.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedMessageResponse(
    val messages: List<MessageResponse>,
    val hasMore: Boolean,
    val total: Int,
)

@Serializable
data class MessageResponse(
    val id: Int,
    val isUnread: Boolean,
    val receiverId: Int,
    val senderId: Int,
    val text: String,
    val timestamp: Long,
)
