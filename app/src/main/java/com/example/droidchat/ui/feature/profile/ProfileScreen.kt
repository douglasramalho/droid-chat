package com.example.droidchat.ui.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.droidchat.R
import com.example.droidchat.ui.components.ChatScaffold
import com.example.droidchat.ui.components.ChatTopAppBar
import com.example.droidchat.ui.components.PrimaryButton
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    ProfileScreen(
        onLogoutClicked = viewModel::signOut
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogoutClicked: () -> Unit,
) {
    ChatScaffold(
        topBar = {
            ChatTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.feature_profile_title)
                    )
                },
            )
        },
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            PrimaryButton(
                text = stringResource(R.string.feature_profile_logout),
                onClick = onLogoutClicked,
            )
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    DroidChatTheme {
        ProfileScreen(
            onLogoutClicked = {}
        )
    }
}