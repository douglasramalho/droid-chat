package com.example.droidchat.data.network.ws

import android.util.Log
import com.example.droidchat.data.network.model.ActiveUserIdsResponse
import com.example.droidchat.data.network.model.MessageResponse
import com.example.droidchat.data.network.model.MessageSocketDataRequest
import com.example.droidchat.data.network.model.MessageSocketRequest
import com.example.droidchat.data.network.model.WebSocketData
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import java.time.Instant
import java.util.UUID
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
        if (socketSession == null) {
            Log.w(tag, "WebSocket session is null")
            return flowOf(SocketMessageResult.ConnectionError(Throwable("WebSocket session is null")))
        }

        return socketSession!!
            .incoming
            .receiveAsFlow()
            .filterIsInstance(Frame.Text::class)
            .map { frame ->
                val text = frame.readText()
                val webSocketData = Json.decodeFromString<WebSocketData>(text)
                Log.d(tag, "Received data: $webSocketData")
                when (val data = webSocketData.data) {
                    is MessageResponse -> SocketMessageResult.MessageReceived(data)

                    is ActiveUserIdsResponse -> SocketMessageResult.ActiveUsersChanged(data)

                    else -> SocketMessageResult.NotHandledYet
                }
            }.catch {
                Log.e(tag, "WebSocket error: $it")
                flowOf(SocketMessageResult.ConnectionError(it))
            }
    }

    override suspend fun sendMessage(receiverId: Int, message: String) {
        if (socketSession == null || socketSession?.isActive == false) {
            Log.w(tag, "WebSocket session is null or not active. Cannot send message.")
            throw IllegalStateException("WebSocket session is null or not active")
        }

        try {
            Log.d(tag, "Sending message: $message")
            val messageRequest = MessageSocketDataRequest(
                type = "messageRequest",
                data = MessageSocketRequest(
                    messageId = UUID.randomUUID().toString(),
                    receiverId = receiverId,
                    text = message,
                    timestamp = Instant.now().toEpochMilli()
                )
            )

            socketSession?.sendSerialized(messageRequest)
        } catch (e: Exception) {
            Log.d(tag, "Error sending message: $e")
            throw e
        }
    }

    override suspend fun disconnect() {
        socketSession?.close()
        socketSession = null
        Log.d(tag, "WebSocket disconnected.")
    }
}