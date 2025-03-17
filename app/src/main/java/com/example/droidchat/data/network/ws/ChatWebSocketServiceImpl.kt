package com.example.droidchat.data.network.ws

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.close
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import javax.inject.Inject

class ChatWebSocketServiceImpl @Inject constructor(
    private val client: HttpClient
) : ChatWebSocketService {

    private var socketSession: DefaultClientWebSocketSession? = null

    private val webSocketUrl = "ws://chat-api.androidmoderno.com.br:8080/chat/"
    private val tag = "ChatWebSocketService"

    override suspend fun connect(userId: Int) {
        if (socketSession != null) {
            Log.w(tag, "Already connected. Skipping new connection attempt.")
            return
        }

        Log.d(tag, "Connecting to WebSocket...")

        try {
            socketSession = client.webSocketSession {
                url("$webSocketUrl$userId")
            }

            if (socketSession?.isActive == true) {
                Log.d(tag, "Connected to WebSocket successfully.")
            } else {
                Log.w(tag, "Failed to connect to WebSocket.")
            }
        } catch (e: Exception) {
            Log.e(tag, "WebSocket connection failed: $e")
        }
    }

    override fun observerSocketMessageResultFlow(): Flow<SocketMessageResult> {
        return flowOf(SocketMessageResult.NotHandledYet)
    }

    override suspend fun sendMessage(receiverId: Int, message: String) {
        if (socketSession == null || socketSession?.isActive == false) {
            Log.w(tag, "WebSocket session is null or not active. Cannot send message.")
            throw IllegalStateException("WebSocket session is null or not active")
        }

        // send message
    }

    override suspend fun disconnect() {
        socketSession?.close()
        socketSession = null
        Log.d(tag, "WebSocket disconnected.")
    }
}