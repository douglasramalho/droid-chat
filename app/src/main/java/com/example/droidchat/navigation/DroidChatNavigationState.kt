package com.example.droidchat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.droidchat.ui.feature.chats.navigateToChats
import com.example.droidchat.ui.feature.users.navigateToUsers

@Composable
fun rememberDroidChatNavigationState(
    navController: NavHostController = rememberNavController()
): DroidChatNavigationState {
    return remember(navController) {
        DroidChatNavigationState(navController)
    }
}

@Stable
class DroidChatNavigationState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable
        get() = TopLevelDestination.entries.firstOrNull { topLevelDestination ->
            currentDestination?.hasRoute(topLevelDestination.route) == true
        }

    val topLevelDestinations = TopLevelDestination.entries

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.CHATS -> navController.navigateToChats(topLevelNavOptions)

            TopLevelDestination.PLUS_BUTTON -> navController.navigateToUsers(topLevelNavOptions)

            TopLevelDestination.PROFILE -> {
            }
        }
    }
}