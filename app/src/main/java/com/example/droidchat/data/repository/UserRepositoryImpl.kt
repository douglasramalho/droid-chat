package com.example.droidchat.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.droidchat.data.di.IoDispatcher
import com.example.droidchat.data.mapper.asDomainModel
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.pagingsource.UserPagingSource
import com.example.droidchat.data.util.safeCallResult
import com.example.droidchat.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : UserRepository {

    override suspend fun getUser(userId: Int): Result<User> {
        return safeCallResult(ioDispatcher) {
            val userResponse = networkDataSource.getUser(userId)
            userResponse.asDomainModel()
        }
    }

    override fun getUsers(limit: Int): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                prefetchDistance = 1,
                pageSize = limit,
                initialLoadSize = limit,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { UserPagingSource(networkDataSource) }
        ).flow
    }
}