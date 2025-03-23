package com.example.droidchat.data.manager.di

import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.manager.selfuser.SelfUserManagerImpl
import com.example.droidchat.data.manager.token.TokenManager
import com.example.droidchat.data.manager.token.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TokenManagerModule {

    @Binds
    @Singleton
    fun bindTokenManager(tokenManager: TokenManagerImpl): TokenManager

    @Binds
    @Singleton
    fun bindSelfUserManager(selfUserManager: SelfUserManagerImpl): SelfUserManager
}