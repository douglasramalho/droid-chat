package com.example.droidchat.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ActiveUserIdsResponse(
    val activeUserIds: List<Int>
)
