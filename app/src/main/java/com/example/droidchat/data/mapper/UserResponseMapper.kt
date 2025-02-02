package com.example.droidchat.data.mapper

import com.example.droidchat.data.network.model.PaginatedUserResponse
import com.example.droidchat.model.User

fun PaginatedUserResponse.asDomainModel(): List<User> = this.users.map { userResponse ->
    User(
        id = userResponse.id,
        self = false,
        firstName = userResponse.firstName,
        lastName = userResponse.lastName,
        profilePictureUrl = userResponse.profilePictureUrl,
        username = userResponse.username,
    )
}