package com.example.droidchat.data.pagingsource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.droidchat.data.database.DatabaseDataSource
import com.example.droidchat.data.database.DroidChatDatabase
import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.data.database.entity.MessageRemoteKeyEntity
import com.example.droidchat.data.mapper.asEntityModel
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.PaginationParams

@OptIn(ExperimentalPagingApi::class)
class MessageRemoteMediator(
    private val networkDataSource: NetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
    private val database: DroidChatDatabase,
    private val receiverId: Int
) : RemoteMediator<Int, MessageEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = databaseDataSource.getMessageRemoteKey(receiverId)
                    remoteKey?.nextOffset ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val limit = state.config.pageSize
            val paginationParams = PaginationParams(
                offset = offset.toString(),
                limit = limit.toString()
            )

            val response = networkDataSource.getMessages(
                receiverId = receiverId,
                paginationParams = paginationParams
            )

            val entities = response.asEntityModel()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    databaseDataSource.clearMessageRemoteKey(receiverId)
                    databaseDataSource.clearMessages(receiverId)
                }

                databaseDataSource.insertMessageRemoteKey(
                    MessageRemoteKeyEntity(
                        receiverId = receiverId,
                        nextOffset = if (response.hasMore) {
                            offset + limit
                        } else null
                    )
                )

                databaseDataSource.insertMessages(
                    messages = entities
                )
            }

            MediatorResult.Success(endOfPaginationReached = !response.hasMore)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}