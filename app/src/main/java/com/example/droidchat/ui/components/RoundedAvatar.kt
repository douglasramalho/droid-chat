package com.example.droidchat.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.droidchat.R
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun RoundedAvatar(
    imageUri: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    defaultImageResId: Int = R.drawable.no_profile_image
) {
    AsyncImage(
        model = imageUri,
        contentDescription = contentDescription,
        modifier = modifier
            .clip(CircleShape)
            .size(size),
        placeholder = painterResource(defaultImageResId),
        error = painterResource(defaultImageResId),
        fallback = painterResource(defaultImageResId),
    )
}

@Preview
@Composable
private fun RoundedAvatarPreview() {
    DroidChatTheme {
        RoundedAvatar(
            imageUri = null,
            contentDescription = null,
        )
    }
}