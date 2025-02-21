package com.example.droidchat.data.mapper

import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.data.network.model.PaginatedMessageResponse

fun PaginatedMessageResponse.asEntityModel(): List<MessageEntity> = this.messages.map { message ->
    MessageEntity(
        id = message.id,
        isUnread = message.isUnread,
        receiverId = message.receiverId,
        senderId = message.senderId,
        text = message.text,
        timestamp = message.timestamp,
    )
}