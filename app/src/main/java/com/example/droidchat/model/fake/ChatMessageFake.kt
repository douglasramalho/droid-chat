package com.example.droidchat.model.fake

import com.example.droidchat.model.ChatMessage

val chatMessage1 = ChatMessage(
    autoId = 1,
    id = 1,
    senderId = 1,
    receiverId = 2,
    text = "Olá",
    formattedDateTime = "15:00",
    isUnread = true,
    isSelf = true,
)

val chatMessage2 = ChatMessage(
    autoId = 2,
    id = 2,
    senderId = 2,
    receiverId = 1,
    text = "Oi, tudo bem?",
    formattedDateTime = "15:05",
    isUnread = true,
    isSelf = false,
)

val chatMessage3 = ChatMessage(
    autoId = 3,
    id = 3,
    senderId = 1,
    receiverId = 2,
    text = "Tudo bem sim, e com você?",
    formattedDateTime = "15:07",
    isUnread = false,
    isSelf = true,
)

val chatMessage4 = ChatMessage(
    autoId = 4,
    id = 4,
    senderId = 2,
    receiverId = 1,
    text = "Estou bem também, obrigado!",
    formattedDateTime = "15:10",
    isUnread = false,
    isSelf = false,
)

val chatMessage5 = ChatMessage(
    autoId = 5,
    id = 5,
    senderId = 1,
    receiverId = 2,
    text = "Que bom! Vamos marcar de sair qualquer dia desses?",
    formattedDateTime = "15:15",
    isUnread = false,
    isSelf = true,
)

