package com.example.droidchat.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.droidchat.model.Chat
import com.example.droidchat.model.fake.chat1
import com.example.droidchat.model.fake.chat2
import com.example.droidchat.model.fake.chat3

class ChatPreviewParameterProvider : PreviewParameterProvider<Chat> {
    override val values: Sequence<Chat> = sequenceOf(
        chat1,
        chat2,
        chat3,
    )
}

class ChatListPreviewParameterProvider : PreviewParameterProvider<List<Chat>> {
    override val values: Sequence<List<Chat>> = sequenceOf(
        listOf(
            chat1,
            chat2,
            chat3,
        )
    )
}