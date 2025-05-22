package com.example.droidchat

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import com.example.droidchat.navigation.Route
import com.example.droidchat.navigation.rememberDroidChatNavigationState
import com.example.droidchat.ui.ChatApp
import com.example.droidchat.ui.theme.DroidChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            DroidChatTheme {
                val navigationState = rememberDroidChatNavigationState()
                navController = navigationState.navController

                val startDestination = if (intent.data == null) Route.SplashRoute else Route.ChatsRoute
                navigationState.startDestination = startDestination

                ChatApp(
                    navigationState = navigationState,
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (intent.action == Intent.ACTION_VIEW && intent.data != null && intent.data!!.scheme == "droidchat") {
            val data = intent.data!!
            when (data.host) {
                "chat_detail" -> {
                    val userId = data.lastPathSegment?.toInt() ?: return
                    navController.navigate(Route.ChatDetailRoute(userId))
                }
            }
        }
    }
}