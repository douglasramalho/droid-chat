package com.example.droidchat.data.repository

import com.example.droidchat.data.di.IoDispatcher
import com.example.droidchat.data.manager.selfuser.SelfUserManager
import com.example.droidchat.data.manager.token.TokenManager
import com.example.droidchat.data.mapper.asDomainModel
import com.example.droidchat.data.network.NetworkDataSource
import com.example.droidchat.data.network.model.AuthRequest
import com.example.droidchat.data.network.model.CreateAccountRequest
import com.example.droidchat.model.CreateAccount
import com.example.droidchat.model.Image
import com.example.droidchat.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val tokenManager: TokenManager,
    private val selfUserManager: SelfUserManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {

    override val currentUserFlow: Flow<User>
        get() = selfUserManager
            .selfUserFlow
            .flowOn(ioDispatcher)
            .map {
                it.asDomainModel()
            }

    override suspend fun getAccessToken(): String? {
        return tokenManager.accessToken.firstOrNull()
    }

    override suspend fun clearAccessToken() {
        withContext(ioDispatcher) {
            tokenManager.clearAccessToken()
        }
    }

    override suspend fun signUp(createAccount: CreateAccount): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
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
        }
    }

    override suspend fun signIn(username: String, password: String): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
                val tokenResponse = networkDataSource.signIn(
                    request = AuthRequest(
                        username = username,
                        password = password,
                    )
                )

                tokenManager.saveAccessToken(tokenResponse.token)

                authenticate().getOrThrow()
            }
        }
    }

    override suspend fun uploadProfilePicture(filePath: String): Result<Image> {
        return withContext(ioDispatcher) {
            runCatching {
                val imageResponse = networkDataSource.uploadProfilePicture(filePath)

                Image(
                    id = imageResponse.id,
                    name = imageResponse.name,
                    type = imageResponse.type,
                    url = imageResponse.url,
                )
            }
        }
    }

    override suspend fun authenticate(): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
                val userResponse = networkDataSource.authenticate()

                selfUserManager.saveSelfUserData(
                    id = userResponse.id,
                    firstName = userResponse.firstName,
                    lastName = userResponse.lastName,
                    profilePictureUrl = userResponse.profilePictureUrl ?: "",
                    username = userResponse.username,
                )
            }
        }
    }
}