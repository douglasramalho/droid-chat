package com.example.droidchat.data.repository

import com.example.droidchat.data.di.IoDispatcher
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.mapper.asDomainModel
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.PaginationParams
import com.example.droidchat.model.Chat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val selfUserManager: SelfUserManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ChatRepository {

    override suspend fun getChats(offset: Int, limit: Int): Result<List<Chat>> {
        return withContext(ioDispatcher) {
            runCatching {
                val paginatedChatResponse = networkDataSource.getChats(
                    paginationParams = PaginationParams(
                        offset = offset.toString(),
                        limit = limit.toString()
                    )
                )

                val selfUser = selfUserManager.selfUserFlow.firstOrNull()
                paginatedChatResponse.asDomainModel(selfUser?.id)
            }
        }
    }
}