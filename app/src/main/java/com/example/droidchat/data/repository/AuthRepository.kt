package com.example.droidchat.data.repository

import com.example.droidchat.model.CreateAccount

interface AuthRepository {

    suspend fun signUp(createAccount: CreateAccount)

    suspend fun signIn(username: String, password: String)
}