package com.example.droidchat.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val autoId: Int = 0,
    val id: Int?,
    @ColumnInfo(name = "is_unread")
    val isUnread: Boolean,
    @ColumnInfo(name = "receiver_id")
    val receiverId: Int,
    @ColumnInfo(name = "sender_id")
    val senderId: Int,
    val text: String,
    val timestamp: Long,
)
