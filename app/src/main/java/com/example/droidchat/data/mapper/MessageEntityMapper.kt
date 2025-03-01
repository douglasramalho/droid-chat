package com.example.droidchat.data.mapper

import com.example.droidchat.data.database.entity.MessageEntity
import com.example.droidchat.model.ChatMessage

fun MessageEntity.asDomainModel(selfUserId: Int?): ChatMessage {
    return ChatMessage(
        autoId = this.autoId,
        id = this.id,
        senderId = this.senderId,
        receiverId = this.receiverId,
        text = this.text,
        formattedDateTime = this.timestamp.toTimestamp(),
        isUnread = this.isUnread,
        isSelf = this.senderId == selfUserId
    )
}

