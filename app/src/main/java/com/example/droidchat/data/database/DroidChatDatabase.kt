package com.example.droidchat.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.droidchat.data.database.dao.MessageDao
import com.example.droidchat.data.database.dao.MessageRemoteKeyDao
import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.data.database.entity.MessageRemoteKeyEntity

@Database(
    entities = [
        MessageEntity::class,
        MessageRemoteKeyEntity::class,
    ],
    version = 1,
)
abstract class DroidChatDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    abstract fun messageRemoteKeyDao(): MessageRemoteKeyDao
}