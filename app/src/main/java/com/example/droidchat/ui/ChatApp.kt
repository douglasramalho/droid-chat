package com.example.droidchat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.droidchat.navigation.ChatNavHost
import com.example.droidchat.navigation.rememberDroidChatNavigationState

@Composable
fun ChatApp() {
    val navigationState = rememberDroidChatNavigationState()

    Scaffold(
        bottomBar = {
            // Vazio
        }
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