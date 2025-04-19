package com.example.droidchat.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object SplashRoute : Route

    @Serializable
    object SignInRoute

    @Serializable
    object SignUpRoute

    @Serializable
    data object ChatsRoute : Route

    @Serializable
    object UsersRoute

    @Serializable
    object ProfileRoute

    @Serializable
    data class ChatDetailRoute(val userId: Int)
}