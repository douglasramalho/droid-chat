package com.example.droidchat.model

data class Chat(
    val id: Int,
    val lastMessage: String?,
    val members: List<User>,
    val unreadCount: Int,
    val timestamp: String,
) {
    val otherMember = members.first { it.self.not() }
}
