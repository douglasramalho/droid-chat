package com.example.droidchat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DroidChatApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHAT_MESSAGES_CHANNEL_ID,
            getString(R.string.notification_chat_messages_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHAT_MESSAGES_CHANNEL_ID = "chat_messages"
    }
}