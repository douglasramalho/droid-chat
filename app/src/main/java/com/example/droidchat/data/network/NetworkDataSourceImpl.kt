package com.example.droidchat.data.network

import com.example.droidchat.data.network.model.AuthRequest
import com.example.droidchat.data.network.model.CreateAccountRequest
import com.example.droidchat.data.network.model.TokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient
) : NetworkDataSource {

    override suspend fun signUp(request: CreateAccountRequest) {
        handleNetworkException {
            httpClient.post("signup") {
                setBody(request)
            }.body<Unit>()
        }
    }

    override suspend fun signIn(request: AuthRequest): TokenResponse {
        return handleNetworkException {
            httpClient.post("signin") {
                setBody(request)
            }.body()
        }
    }
}