package com.example.droidchat.data.util

import com.example.droidchat.model.NotificationData
import kotlinx.serialization.json.Json
import javax.inject.Inject

class NotificationPayloadParse @Inject constructor() {

    fun parse(notificationPayloadJsonString: String): NotificationData {
        return Json.decodeFromString(notificationPayloadJsonString)
    }
}