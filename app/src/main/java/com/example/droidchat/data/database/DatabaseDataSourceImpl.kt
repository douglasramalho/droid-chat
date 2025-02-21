package com.example.droidchat.data.database

import androidx.paging.PagingSource
import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.data.database.entity.MessageRemoteKeyEntity
import javax.inject.Inject

class DatabaseDataSourceImpl @Inject constructor(
    database: DroidChatDatabase
) : DatabaseDataSource  {

    private val messageDao = database.messageDao()
    private val messageRemoteKeyDao = database.messageRemoteKeyDao()

    override fun getPagedMessages(receiverId: Int): PagingSource<Int, MessageEntity> {
        return messageDao.getPagedMessages(receiverId)
    }

    override suspend fun insertMessages(messages: List<MessageEntity>) {
        messageDao.insertMessages(messages)
    }

    override suspend fun clearMessages(receiverId: Int) {
        messageDao.clearMessages(receiverId)
    }

    override suspend fun getMessageRemoteKey(receiverId: Int): MessageRemoteKeyEntity? {
        return messageRemoteKeyDao.getRemoteKey(receiverId)
    }

    override suspend fun insertMessageRemoteKey(remoteKey: MessageRemoteKeyEntity) {
        messageRemoteKeyDao.insertRemoteKey(remoteKey)
    }

    override suspend fun clearMessageRemoteKey(receiverId: Int) {
        messageRemoteKeyDao.clearMessageRemoteKey(receiverId)
    }
}