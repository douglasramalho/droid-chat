package com.example.droidchat.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

const val SPLASH_ROUTE = "splash"
const val SIGN_IN_ROUTE = "signIn"
const val SIGN_UP_ROUTE = "signUp"

@Composable
fun ChatNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SPLASH_ROUTE) {
        composable(SPLASH_ROUTE) {
            Text(
                text = "Hello World!",
            )
        }
        composable(SIGN_IN_ROUTE) {

        }
        composable(SIGN_UP_ROUTE) {

        }
    }
}