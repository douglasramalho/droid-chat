package com.example.droidchat.data.mapper

import com.example.droidchat.SelfUser
import com.example.droidchat.model.User

fun SelfUser.asDomainModel() = User(
    id = this.id,
    self = true,
    firstName = this.firstName,
    lastName = this.lastName,
    profilePictureUrl = this.profilePictureUrl,
    username = this.username,
)