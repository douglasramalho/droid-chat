package com.example.droidchat.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidchat.model.User
import com.example.droidchat.model.fake.user2
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun UserItem(
    user: User,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedAvatar(
            imageUri = user.profilePictureUrl,
            contentDescription = null,
            size = 42.dp,
        )

        Text(
            text = user.firstName,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserItemPreview() {
    DroidChatTheme {
        UserItem(
            user = user2
        )
    }
}