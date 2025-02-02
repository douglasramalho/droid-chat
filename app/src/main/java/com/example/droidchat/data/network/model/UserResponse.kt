package com.example.droidchat.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedUserResponse(
    val users: List<UserResponse>,
    val hasMore: Boolean,
    val total: Int,
)

@Serializable
data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val profilePictureUrl: String?,
    val username: String,
)
