package com.example.droidchat.ui.feature.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.droidchat.R
import com.example.droidchat.model.User
import com.example.droidchat.model.fake.user2
import com.example.droidchat.model.fake.user3
import com.example.droidchat.model.fake.user4
import com.example.droidchat.ui.components.AnimatedContent
import com.example.droidchat.ui.components.ChatScaffold
import com.example.droidchat.ui.components.ChatTopAppBar
import com.example.droidchat.ui.components.GeneralEmptyList
import com.example.droidchat.ui.components.GeneralError
import com.example.droidchat.ui.components.PrimaryButton
import com.example.droidchat.ui.components.UserItem
import com.example.droidchat.ui.theme.DroidChatTheme
import com.example.droidchat.ui.theme.Grey1
import kotlinx.coroutines.flow.flowOf

@Composable
fun UsersRoute(
    viewModel: UsersViewModel = hiltViewModel(),
    navigateToChatDetail: (userId: Int) -> Unit,
) {
    val pagingUsers = viewModel.usersFlow.collectAsLazyPagingItems()
    UsersScreen(
        pagingUsers = pagingUsers,
        onUserClicked = navigateToChatDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    pagingUsers: LazyPagingItems<User>,
    onUserClicked: (userId: Int) -> Unit,
) {
    ChatScaffold(
        topBar = {
            ChatTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.feature_users_title),
                    )
                }
            )
        }
    ) {
        when (pagingUsers.loadState.refresh) {
            LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            is LoadState.NotLoading -> {
                if (pagingUsers.itemCount == 0) {
                    GeneralEmptyList(
                        message = stringResource(R.string.feature_users_empty_list),
                        resource = {
                            AnimatedContent(
                                resId = R.raw.animation_empty_list
                            )
                        }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(pagingUsers.itemCount) { index ->
                            val user = pagingUsers[index]
                            if (user != null) {
                                UserItem(
                                    user = user,
                                    modifier = Modifier
                                        .clickable {
                                            onUserClicked(user.id)
                                        }
                                )

                                if (index < pagingUsers.itemCount - 1) {
                                    HorizontalDivider(
                                        modifier = Modifier
                                            .padding(top = 16.dp),
                                        color = Grey1
                                    )
                                }
                            }
                        }

                        if (pagingUsers.loadState.append is LoadState.Loading) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        if (pagingUsers.loadState.append is LoadState.Error) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = stringResource(R.string.feature_users_error_loading_more),
                                        modifier = Modifier
                                            .padding(vertical = 8.dp),
                                        color = MaterialTheme.colorScheme.error
                                    )

                                    PrimaryButton(
                                        text = stringResource(R.string.common_try_again),
                                        onClick = {
                                            pagingUsers.retry()
                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 30.dp)
                                            .height(46.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is LoadState.Error -> {
                GeneralError(
                    title = stringResource(R.string.common_generic_error_title),
                    message = stringResource(R.string.common_generic_error_message),
                    resource = {
                        AnimatedContent()
                    },
                    action = {
                        PrimaryButton(
                            text = stringResource(R.string.common_try_again),
                            onClick = {
                                pagingUsers.refresh()
                            }
                        )
                    }
                )
            }
        }



    }
}

@Preview
@Composable
private fun UsersScreenPreview() {
    DroidChatTheme {
        val usersFlow = flowOf(
            PagingData.from(
                listOf(
                    user2,
                    user3,
                    user4
                ),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false)
                )
            )
        )

        UsersScreen(
            pagingUsers = usersFlow.collectAsLazyPagingItems(),
            onUserClicked = {},
        )
    }
}