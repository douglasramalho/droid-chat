package com.example.droidchat.service

import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.droidchat.DroidChatApp
import com.example.droidchat.MainActivity
import com.example.droidchat.R
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.util.NotificationPayloadParse
import com.example.droidchat.model.NotificationData
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

    @Inject
    lateinit var notificationPayloadParse: NotificationPayloadParse

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
                notificationPayloadJsonString?.let { payloadString ->
                    val notificationData = notificationPayloadParse.parse(payloadString)
                    sendNotification(notificationData)
                }
            }
        }
    }

    private fun sendNotification(notificationData: NotificationData) {
        if (ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.POST_NOTIFICATIONS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            return
        }

        val intent = Intent(applicationContext, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(applicationContext, DroidChatApp.CHAT_MESSAGES_CHANNEL_ID)
            .setContentTitle(notificationData.userName)
            .setContentText(notificationData.message)
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        notificationManager.notify(notificationData.userId, notificationBuilder.build())
    }
}