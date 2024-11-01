package com.example.droidchat.data.repository

import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.AuthRequest
import com.example.droidchat.data.network.model.CreateAccountRequest
import com.example.droidchat.model.CreateAccount
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
) : AuthRepository {

    override suspend fun signUp(createAccount: CreateAccount) {
        networkDataSource.signUp(
            request = CreateAccountRequest(
                username = createAccount.username,
                password = createAccount.password,
                firstName = createAccount.firstName,
                lastName = createAccount.lastName,
                profilePictureId = createAccount.profilePictureId,
            )
        )
    }

    override suspend fun signIn(username: String, password: String) {
        networkDataSource.signIn(
            request = AuthRequest(
                username = username,
                password = password,
            )
        )
    }
}