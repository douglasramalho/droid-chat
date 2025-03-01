package com.example.droidchat.data.repository

import androidx.paging.PagingData
import com.example.droidchat.model.Chat
import com.example.droidchat.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>>

    fun getPagedMessages(receiverId: Int): Flow<PagingData<ChatMessage>>

    suspend fun sendMessage(receiverId: Int, message: String)
}