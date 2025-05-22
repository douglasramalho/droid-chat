package com.example.droidchat

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DroidChatApp : Application(), DefaultLifecycleObserver {

    override fun onCreate() {
        super<Application>.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
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

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        onAppForeground = true
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        onAppForeground = false
    }

    companion object {
        const val CHAT_MESSAGES_CHANNEL_ID = "chat_messages"
        var onAppForeground: Boolean = false
            private set
    }
}