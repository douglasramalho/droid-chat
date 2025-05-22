package com.example.droidchat.data.repository

import androidx.paging.PagingData
import com.example.droidchat.data.network.ws.SocketMessageResult
import com.example.droidchat.model.Chat
import com.example.droidchat.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    val newMessageReceivedFlow: Flow<Unit>

    suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>>

    fun getPagedMessages(receiverId: Int): Flow<PagingData<ChatMessage>>

    suspend fun sendMessage(receiverId: Int, message: String): Result<Unit>

    suspend fun connectWebSocket(): Result<Unit>

    fun observeSocketMessageResultFlow(): Flow<SocketMessageResult>

    suspend fun disconnectWebsocket()
}