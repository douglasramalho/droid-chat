package com.example.droidchat.ui.feature.users

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.droidchat.navigation.Route

fun NavController.navigateToUsers(
    navOptions: NavOptions? = null
) {
    this.navigate(Route.UsersRoute, navOptions)
}