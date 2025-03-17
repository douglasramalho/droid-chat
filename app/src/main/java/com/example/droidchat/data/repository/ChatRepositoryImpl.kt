package com.example.droidchat.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.droidchat.data.database.DatabaseDataSource
import com.example.droidchat.data.database.DroidChatDatabase
import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.data.di.IoDispatcher
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.mapper.asDomainModel
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.PaginationParams
import com.example.droidchat.data.network.ws.ChatWebSocketService
import com.example.droidchat.data.network.ws.SocketMessageResult
import com.example.droidchat.data.pagingsource.MessageRemoteMediator
import com.example.droidchat.data.util.safeCallResult
import com.example.droidchat.model.Chat
import com.example.droidchat.model.ChatMessage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
    private val database: DroidChatDatabase,
    private val selfUserManager: SelfUserManager,
    private val chatWebSocketService: ChatWebSocketService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ChatRepository {

    val selfUser = runBlocking { selfUserManager.selfUserFlow.firstOrNull() }

    override suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>> {
        return withContext(ioDispatcher) {
            runCatching {
                val paginatedChatResponse = networkDataSource.getChats(
                    paginationParams = PaginationParams(
                        offset = offset.toString(),
                        limit = limit.toString()
                    )
                )

                paginatedChatResponse.asDomainModel(selfUser?.id)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedMessages(receiverId: Int): Flow<PagingData<ChatMessage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = MessageRemoteMediator(
                networkDataSource = networkDataSource,
                databaseDataSource = databaseDataSource,
                database = database,
                receiverId = receiverId,
            ),
            pagingSourceFactory = {
                databaseDataSource.getPagedMessages(receiverId)
            }
        ).flow.map {
            it.map { messageEntity ->
                messageEntity.asDomainModel(selfUserId = selfUser?.id)
            }
        }
    }

    override suspend fun sendMessage(receiverId: Int, message: String): Result<Unit> {
        return safeCallResult(ioDispatcher) {
            chatWebSocketService.sendMessage(receiverId, message)
        }
    }

    override suspend fun connectWebSocket(): Result<Unit> {
        return safeCallResult(ioDispatcher) {
            chatWebSocketService.connect(selfUser?.id ?: 0)
        }
    }

    override fun observeSocketMessageResultFlow(): Flow<SocketMessageResult> {
        return chatWebSocketService.observerSocketMessageResultFlow()
            .onEach { socketMessageResult ->
                when (socketMessageResult) {
                    is SocketMessageResult.MessageReceived -> {
                        val messageResponse = socketMessageResult.message
                        val messageEntity = MessageEntity(
                            id = messageResponse.id,
                            isUnread = messageResponse.isUnread,
                            senderId = selfUser?.id ?: 0,
                            receiverId = messageResponse.receiverId,
                            text = messageResponse.text,
                            timestamp = messageResponse.timestamp
                        )

                        databaseDataSource.insertMessages(listOf(messageEntity))
                    }

                    else -> {
                    }
                }
            }
    }

    override suspend fun disconnectWebsocket() {
        withContext(ioDispatcher) {
            chatWebSocketService.disconnect()
        }
    }
}