package com.example.droidchat.model

data class ChatMessage(
    val autoId: Int,
    val id: Int?,
    val senderId: Int,
    val receiverId: Int,
    val text: String,
    val formattedDateTime: String,
    val isUnread: Boolean,
    val isSelf: Boolean,
)
