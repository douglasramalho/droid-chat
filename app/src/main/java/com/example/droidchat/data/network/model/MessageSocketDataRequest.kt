package com.example.droidchat.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageSocketDataRequest(
    val type: String,
    val data: MessageSocketRequest
)

@Serializable
data class MessageSocketRequest(
    val messageId: String,
    val receiverId: Int,
    val text: String,
    val timestamp: Long
)
