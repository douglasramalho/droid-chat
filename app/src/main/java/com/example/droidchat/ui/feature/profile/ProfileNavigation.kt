package com.example.droidchat.ui.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.droidchat.navigation.Route

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null
) {
    this.navigate(Route.ProfileRoute, navOptions)
}