package com.example.droidchat.data.mapper

import com.example.droidchat.data.network.model.UserResponse
import com.example.droidchat.model.User

fun UserResponse.asDomainModel() = User(
    id = this.id,
    self = false,
    firstName = this.firstName,
    lastName = this.lastName,
    profilePictureUrl = this.profilePictureUrl,
    username = this.username,
)