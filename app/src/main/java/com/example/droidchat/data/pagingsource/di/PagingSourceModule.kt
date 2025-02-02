package com.example.droidchat.data.pagingsource.di

import androidx.paging.PagingSource
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.pagingsource.UserPagingSource
import com.example.droidchat.model.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagingSourceModule {

    @Provides
    @Singleton
    fun provideUserPagingSource(
        networkDataSource: NetworkDataSource
    ): PagingSource<Int, User> = UserPagingSource(networkDataSource)
}