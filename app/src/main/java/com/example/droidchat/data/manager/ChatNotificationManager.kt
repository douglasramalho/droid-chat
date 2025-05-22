package com.example.droidchat.data.manager

import com.example.droidchat.model.NotificationData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatNotificationManager @Inject constructor() {

    private val _incomingMessageFlow = MutableSharedFlow<NotificationData>(replay = 1)
    val incomingMessageFlow = _incomingMessageFlow.asSharedFlow()

    fun notifyNewMessage(notificationData: NotificationData) {
        if (_incomingMessageFlow.subscriptionCount.value > 0) {
            _incomingMessageFlow.tryEmit(notificationData)
        }
    }
}