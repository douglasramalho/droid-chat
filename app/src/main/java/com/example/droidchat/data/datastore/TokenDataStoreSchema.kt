package com.example.droidchat.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.tokenDataStore by preferencesDataStore(name = "token_store")

object TokensKeys {
    val ACCESS_TOKEN = stringPreferencesKey("accessToken")
}