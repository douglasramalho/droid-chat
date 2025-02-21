package com.example.droidchat.data.database.di

import com.example.droidchat.data.database.DatabaseDataSource
import com.example.droidchat.data.database.DatabaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseDataSourceModule {

    @Binds
    @Singleton
    fun bindDatabaseDataSource(
        databaseDataSource: DatabaseDataSourceImpl
    ): DatabaseDataSource
}