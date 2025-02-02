package com.example.droidchat.data.repository.di

import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.data.repository.AuthRepositoryImpl
import com.example.droidchat.data.repository.ChatRepository
import com.example.droidchat.data.repository.ChatRepositoryImpl
import com.example.droidchat.data.repository.UserRepository
import com.example.droidchat.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindChatRepository(repository: ChatRepositoryImpl): ChatRepository

    @Binds
    fun bindUserRepository(repository: UserRepositoryImpl): UserRepository
}