package com.example.droidchat.data.network.di

import android.content.Context
import android.content.Intent
import com.example.droidchat.BuildConfig
import com.example.droidchat.MainActivity
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.manager.token.TokenManager
import com.example.droidchat.model.NetworkException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext
        context: Context,
        tokenManager: TokenManager,
        selfUserManager: SelfUserManager,
    ): HttpClient {
        return HttpClient(CIO) {
            expectSuccess = true

            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }

            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = Logger.SIMPLE
                    level = LogLevel.ALL
                }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                url("https://chat-api.androidmoderno.com.br")
                contentType(ContentType.Application.Json)
            }

            HttpResponseValidator {
                handleResponseExceptionWithRequest { cause, _ ->
                    if (cause is ClientRequestException) {
                        if (cause.response.status == HttpStatusCode.Unauthorized) {
                            tokenManager.clearAccessToken()
                            selfUserManager.clearSelfUser()

                            context.startActivity(
                                Intent(context, MainActivity::class.java).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                            )
                        } else {
                            val errorMessage = cause.response.bodyAsText()
                            throw NetworkException.ApiException(errorMessage, cause.response.status.value)
                        }
                    } else {
                        throw NetworkException.UnknownNetworkException(cause)
                    }
                }
            }
        }.apply {
            plugin(HttpSend).intercept { request ->
                val accessToken = tokenManager.accessToken.firstOrNull()
                accessToken?.let {
                    request.headers {
                        append("Authorization", "Bearer $it")
                    }
                }

                execute(request)
            }
        }
    }
}