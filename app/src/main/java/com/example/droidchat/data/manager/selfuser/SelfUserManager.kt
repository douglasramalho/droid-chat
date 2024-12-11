package com.example.droidchat.data.manager.selfuser

import com.example.droidchat.SelfUser
import kotlinx.coroutines.flow.Flow

interface SelfUserManager {

    val selfUserFlow: Flow<SelfUser>

    suspend fun saveSelfUserData(
        firstName: String,
        lastName: String,
        profilePictureUrl: String,
        username: String
    )

    suspend fun clearSelfUser()
}