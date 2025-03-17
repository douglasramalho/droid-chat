package com.example.droidchat.data.network.ws

import kotlinx.coroutines.flow.Flow

interface ChatWebSocketService {

    suspend fun connect(userId: Int)

    fun observerSocketMessageResultFlow(): Flow<SocketMessageResult>

    suspend fun sendMessage(receiverId: Int, message: String)

    suspend fun disconnect()
}