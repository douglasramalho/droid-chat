package com.example.droidchat.data.manager.notification

interface NotificationManager {

    suspend fun getToken(): String
}