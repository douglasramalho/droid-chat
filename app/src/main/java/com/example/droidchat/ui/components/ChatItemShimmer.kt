package com.example.droidchat.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.droidchat.ui.theme.DroidChatTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun ChatItemShimmer(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .shimmer()
    ) {
        val (avatarRef,
            firstNameRef,
            lastMessageRef,
            lastMessageTimeRef,
            unreadCountRef) = createRefs()

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
                .background(Color.Gray)
                .constrainAs(avatarRef) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                },
        )

        Box(
            modifier = Modifier
                .height(16.dp)
                .background(Color.Gray)
                .constrainAs(firstNameRef) {
                    top.linkTo(avatarRef.top)
                    start.linkTo(avatarRef.end, margin = 16.dp)
                    end.linkTo(lastMessageTimeRef.start, margin = 16.dp)
                    bottom.linkTo(lastMessageRef.top)
                    width = Dimension.fillToConstraints
                },
        )

        Box(
            modifier = Modifier
                .height(16.dp)
                .background(Color.Gray)
                .constrainAs(lastMessageRef) {
                    top.linkTo(firstNameRef.bottom)
                    start.linkTo(avatarRef.end, margin = 16.dp)
                    end.linkTo(unreadCountRef.start, margin = 16.dp)
                    bottom.linkTo(avatarRef.bottom)
                    width = Dimension.fillToConstraints
                },
        )

        Box(
            modifier = Modifier
                .size(width = 15.dp, height = 16.dp)
                .background(Color.Gray)
                .constrainAs(lastMessageTimeRef) {
                    top.linkTo(firstNameRef.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(firstNameRef.bottom)
                    width = Dimension.wrapContent
                },
        )

        Box(
            modifier = Modifier
                .size(width = 15.dp, height = 16.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .padding(horizontal = 4.dp)
                .constrainAs(unreadCountRef) {
                    top.linkTo(lastMessageTimeRef.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(lastMessageRef.bottom)
                    width = Dimension.wrapContent
                },
        )
    }
}

@Preview
@Composable
private fun ChatItemShimmerPreview() {
    DroidChatTheme {
        ChatItemShimmer()
    }
}