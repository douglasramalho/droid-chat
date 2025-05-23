package com.example.droidchat.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import com.example.droidchat.navigation.extension.slidOutTo
import com.example.droidchat.navigation.extension.slideInTo
import com.example.droidchat.ui.feature.chatdetail.ChatDetailRoute
import com.example.droidchat.ui.feature.chats.ChatsRoute
import com.example.droidchat.ui.feature.chats.navigateToChats
import com.example.droidchat.ui.feature.signin.SignInRoute
import com.example.droidchat.ui.feature.signup.SignUpRoute
import com.example.droidchat.ui.feature.splash.SplashRoute
import com.example.droidchat.ui.feature.users.UsersRoute

const val CHAT_BASE_DETAIL_URI = "droidchat://chat_detail"

@Composable
fun ChatNavHost(
    navigationState: DroidChatNavigationState
) {
    val navController = navigationState.navController
    val activity = LocalActivity.current

    NavHost(navController = navController, startDestination = navigationState.startDestination) {
        composable<Route.SplashRoute> {
            SplashRoute(
                onNavigateToSignIn = {
                    navController.navigate(
                        route = Route.SignInRoute,
                        navOptions = navOptions {
                            popUpTo(Route.SplashRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
                onNavigateToMain = {
                    navController.navigateToChats(
                        navOptions = navOptions {
                            popUpTo(Route.SplashRoute) {
                                inclusive = true
                            }
                        }
                    )
                },
                onCloseApp = {
                    activity?.finish()
                }
            )
        }
        composable<Route.SignInRoute>(
            enterTransition = {
                this.slideInTo(AnimatedContentTransitionScope.SlideDirection.Right)
            },
            exitTransition = {
                this.slidOutTo(AnimatedContentTransitionScope.SlideDirection.Left)
            }
        ) {
            SignInRoute(
                navigateToSignUp = {
                    navController.navigate(Route.SignUpRoute)
                },
                navigateToMain = {
                    navController.navigateToChats(
                        navOptions = navOptions {
                            popUpTo(Route.SignInRoute) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
        composable<Route.SignUpRoute>(
            enterTransition = {
                this.slideInTo(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                this.slidOutTo(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            SignUpRoute(
                onSignUpSuccess = {
                    navController.popBackStack()
                }
            )
        }
        composable<Route.ChatsRoute> {
            ChatsRoute(
                navigateToChatDetail = { chat ->
                    navController.navigate(Route.ChatDetailRoute(chat.otherMember.id))
                }
            )
        }
        composable<Route.UsersRoute> {
            UsersRoute(
                navigateToChatDetail = { userId ->
                    navController.navigate(Route.ChatDetailRoute(userId))
                }
            )
        }
        composable<Route.ChatDetailRoute>(
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "$CHAT_BASE_DETAIL_URI/{userId}"
                }
            )
        ) {
            ChatDetailRoute(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}