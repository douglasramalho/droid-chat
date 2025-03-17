package com.example.droidchat.data.network.ws

import com.example.droidchat.data.network.model.MessageResponse

sealed interface SocketMessageResult {
    data object NotHandledYet : SocketMessageResult
    data class MessageReceived(val message: MessageResponse) : SocketMessageResult
    data class ConnectionError(val error: Throwable) : SocketMessageResult
}