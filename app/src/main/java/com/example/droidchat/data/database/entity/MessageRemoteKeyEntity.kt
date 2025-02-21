package com.example.droidchat.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message_remote_keys")
data class MessageRemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "receiver_id")
    val receiverId: Int,
    val nextOffset: Int?,
)
