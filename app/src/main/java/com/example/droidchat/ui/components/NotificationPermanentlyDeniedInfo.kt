package com.example.droidchat.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.droidchat.R
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun NotificationPermanentlyDeniedInfo(
    onDismissClick: () -> Unit,
    onGoToSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.notification_request_permission_denied_title),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.notification_request_permission_denied_message),
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                TextButton(
                    onClick = onDismissClick
                ) {
                    Text(
                        text = stringResource(R.string.common_close),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                TextButton(
                    onClick = onGoToSettingsClick
                ) {
                    Text(
                        text = stringResource(R.string.notification_turn_on_text),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NotificationPermanentlyDeniedInfoPreview() {
    DroidChatTheme {
        NotificationPermanentlyDeniedInfo(
            onDismissClick = {},
            onGoToSettingsClick = {},
        )
    }
}