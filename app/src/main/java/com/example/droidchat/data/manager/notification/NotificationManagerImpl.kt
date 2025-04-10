package com.example.droidchat.data.manager.notification

import com.example.droidchat.data.di.IoDispatcher
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationManagerImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : NotificationManager {

    private val firebaseMessaging by lazy {
        Firebase.messaging
    }

    override suspend fun getToken(): String {
        return withContext(ioDispatcher) {
            firebaseMessaging.token.await()
        }
    }
}