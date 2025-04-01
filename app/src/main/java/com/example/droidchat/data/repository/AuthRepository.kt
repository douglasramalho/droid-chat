package com.example.droidchat.data.repository

import com.example.droidchat.model.CreateAccount
import com.example.droidchat.model.Image
import com.example.droidchat.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUserFlow: Flow<User>

    suspend fun getAccessToken(): String?

    suspend fun clearAccessToken()

    suspend fun signUp(createAccount: CreateAccount): Result<Unit>

    suspend fun signIn(username: String, password: String): Result<Unit>

    suspend fun uploadProfilePicture(filePath: String): Result<Image>

    suspend fun authenticate(): Result<Unit>
}