package com.example.droidchat.ui.feature.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.droidchat.R
import com.example.droidchat.model.Chat
import com.example.droidchat.model.User
import com.example.droidchat.ui.components.ChatItem
import com.example.droidchat.ui.theme.DroidChatTheme
import com.example.droidchat.ui.theme.Grey1

@Composable
fun ChatsRoute(
    viewModel: ChatsViewModel = hiltViewModel()
) {
    val chatsListUiState by viewModel.chatsListUiState.collectAsStateWithLifecycle()

    ChatsScreen(
        chatsListUiState = chatsListUiState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen(
    chatsListUiState: ChatsViewModel.ChatsListUiState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = AnnotatedString.fromHtml(stringResource(R.string.feature_chats_greeting, "Douglas")),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                expandedHeight = 100.dp,
            )
        },
        containerColor = MaterialTheme.colorScheme.primary,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraLarge.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp),
                    )
                )
                .clip(
                    shape = MaterialTheme.shapes.extraLarge.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp),
                    )
                )
                .fillMaxSize(),
        ) {
            when (chatsListUiState) {
                ChatsViewModel.ChatsListUiState.Loading -> {

                }

                is ChatsViewModel.ChatsListUiState.Success -> {
                    ChatsListContent(chatsListUiState.chats)
                }

                ChatsViewModel.ChatsListUiState.Error -> {

                }
            }
        }
    }
}

@Composable
fun ChatsListContent(chats: List<Chat>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(chats) { index, chat ->
            ChatItem(chat)

            if (index < chats.lastIndex) {
                HorizontalDivider(
                    color = Grey1
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatsScreenLoadingPreview() {
    DroidChatTheme {
        ChatsScreen(
            chatsListUiState = ChatsViewModel.ChatsListUiState.Loading
        )
    }
}

@Preview
@Composable
private fun ChatsScreenSuccessPreview() {
    DroidChatTheme {
        ChatsScreen(
            chatsListUiState = ChatsViewModel.ChatsListUiState.Success(
                chats = listOf(
                    Chat(
                        id = 1,
                        lastMessage = "Olá!",
                        members = listOf(
                            User(
                                id = 1,
                                self = true,
                                firstName = "Douglas",
                                lastName = "Motta",
                                profilePictureUrl = "",
                                username = "douglas.motta"
                            ),
                            User(
                                id = 2,
                                self = false,
                                firstName = "João",
                                lastName = "Silva",
                                profilePictureUrl = "",
                                username = "joao.silva"
                            )
                        ),
                        unreadCount = 0,
                        timestamp = "12:25"
                    ),
                    Chat(
                        id = 2,
                        lastMessage = "Olá!",
                        members = listOf(
                            User(
                                id = 1,
                                self = true,
                                firstName = "Douglas",
                                lastName = "Motta",
                                profilePictureUrl = "",
                                username = "douglas.motta"
                            ),
                            User(
                                id = 2,
                                self = false,
                                firstName = "João",
                                lastName = "Silva",
                                profilePictureUrl = "",
                                username = "joao.silva"
                            )
                        ),
                        unreadCount = 0,
                        timestamp = "12:25"
                    )
                ),
            )
        )
    }
}

@Preview
@Composable
private fun ChatsScreenErrorPreview() {
    DroidChatTheme {
        ChatsScreen(
            chatsListUiState = ChatsViewModel.ChatsListUiState.Error
        )
    }
}