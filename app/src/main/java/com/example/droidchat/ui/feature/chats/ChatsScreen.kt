package com.example.droidchat.ui.feature.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun ChatsRoute() {
    ChatsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Olá, Douglas")
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(100) {
                Text(text = "Chat $it")
            }
        }
    }
}

@Preview
@Composable
private fun ChatsScreenPreview() {
    DroidChatTheme {
        ChatsScreen()
    }
}