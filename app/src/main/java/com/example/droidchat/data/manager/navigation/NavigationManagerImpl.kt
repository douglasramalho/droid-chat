package com.example.droidchat.data.manager.navigation

import android.content.Context
import android.content.Intent
import com.example.droidchat.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NavigationManagerImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : NavigationManager {
    override fun navigateToSignIn() {
        context.startActivity(
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        )
    }
}