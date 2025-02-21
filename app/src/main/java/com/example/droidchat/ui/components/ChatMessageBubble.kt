package com.example.droidchat.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidchat.model.ChatMessage
import com.example.droidchat.model.fake.chatMessage3
import com.example.droidchat.model.fake.chatMessage4
import com.example.droidchat.model.fake.chatMessage5
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun ChatMessageBubble(
    chatMessage: ChatMessage,
    previousChatMessage: ChatMessage?,
    modifier: Modifier = Modifier
) {
    val isCurrentUserMessage = chatMessage.isSelf
    val isSameUserAsPrevious = previousChatMessage?.senderId == chatMessage.senderId

    val surfaceColor = if  (chatMessage.isSelf) {
        MaterialTheme.colorScheme.tertiary
    } else MaterialTheme.colorScheme.secondary

    val horizontalAlignment = if (isCurrentUserMessage) {
        Alignment.End
    } else Alignment.Start

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = horizontalAlignment
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth(),
            shape =  RoundedCornerShape(100.dp),
            color = surfaceColor,
        ) {
            Text(
                text = chatMessage.text,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        if (!isSameUserAsPrevious) {
            Text(
                text = chatMessage.formattedDateTime,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBubblePreview() {
    DroidChatTheme {
        ChatMessageBubble(
            chatMessage = chatMessage5,
            previousChatMessage = chatMessage4
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatBubbleSenderPreview() {
    DroidChatTheme {
        ChatMessageBubble(
            chatMessage = chatMessage4,
            previousChatMessage = chatMessage3
        )
    }
}