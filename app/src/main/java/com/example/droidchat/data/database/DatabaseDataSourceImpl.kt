package com.example.droidchat.data.database

import androidx.paging.PagingSource
import com.example.droidchat.data.database.entity.MessageEntity
import javax.inject.Inject

class DatabaseDataSourceImpl @Inject constructor(
    database: DroidChatDatabase
) : DatabaseDataSource  {

    private val messageDao = database.messageDao()

    override fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity> {
        return messageDao.getPagedMessages(receiverId)
    }

    override suspend fun insertMessages(messages: List<MessageEntity>) {
        messageDao.insertMessages(messages)
    }

    override suspend fun clearMessages(receiverId: Int) {
        messageDao.clearMessages(receiverId)
    }
}