package com.example.droidchat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidchat.R
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun GeneralError(
    title: String,
    message: String,
    modifier: Modifier = Modifier,
    resource: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        resource?.let {
            Box(
                modifier = Modifier
                    .sizeIn(
                        maxHeight = 200.dp,
                        maxWidth = 200.dp,
                    )
            ) {
                it()
            }

            Spacer(Modifier.height(32.dp))
        }

        Text(
            text = title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge,
        )

        action?.let {
            Spacer(Modifier.height(32.dp))
            it()
        }
    }
}

@Preview
@Composable
private fun GeneralErrorPreview() {
    DroidChatTheme {
        GeneralError(
            title = "Ops!",
            message = "Alguma coisa deu errado",
            resource = {
                Image(
                    painter = painterResource(R.drawable.no_profile_image),
                    contentDescription = null
                )
            }
        )
    }
}