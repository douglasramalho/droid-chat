package com.example.droidchat.service

import android.util.Log
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FcmMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var selfUserManager: SelfUserManager

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        scope.launch {
            val selfUser = selfUserManager.selfUserFlow.firstOrNull()
            if (selfUser?.id != null) {
                val notificationPayloadJsonString = message.data["messagePayload"]
                Log.d("FcmMessagingService", "onMessageReceived: $notificationPayloadJsonString")
            }
        }
    }
}