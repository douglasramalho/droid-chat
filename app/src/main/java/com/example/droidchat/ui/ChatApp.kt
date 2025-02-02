package com.example.droidchat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.droidchat.navigation.ChatNavHost
import com.example.droidchat.navigation.DroidChatNavigationState
import com.example.droidchat.navigation.rememberDroidChatNavigationState
import com.example.droidchat.ui.components.BottomNavigationMenu
import com.example.droidchat.ui.theme.Grey1

@Composable
fun ChatApp(navigationState: DroidChatNavigationState = rememberDroidChatNavigationState()) {
    val topLevelDestinations = remember(navigationState.topLevelDestinations) {
        navigationState.topLevelDestinations.toSet()
    }

    Scaffold(
        bottomBar = {
            if (navigationState.currentTopLevelDestination in topLevelDestinations) {
                BottomNavigationMenu(navigationState = navigationState)
            }
        },
        containerColor = Grey1,
    ) { innerPaddings ->
        Box(
           modifier = Modifier
               .consumeWindowInsets(innerPaddings)
               .padding(innerPaddings)
               .imePadding()
               .fillMaxSize()
        ) {
            ChatNavHost(
                navigationState = navigationState,
            )
        }
    }
}