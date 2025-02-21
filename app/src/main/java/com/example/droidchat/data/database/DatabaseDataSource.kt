package com.example.droidchat.data.database

import androidx.paging.PagingSource
import com.example.droidchat.data.database.entity.MessageEntity

interface DatabaseDataSource {
    fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity>

    suspend fun insertMessages(messages: List<MessageEntity>)

    suspend fun clearMessages(receiverId: Int)

}