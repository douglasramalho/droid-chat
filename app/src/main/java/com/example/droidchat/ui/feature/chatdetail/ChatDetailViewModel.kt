package com.example.droidchat.ui.feature.chatdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.example.droidchat.data.repository.ChatRepository
import com.example.droidchat.data.repository.UserRepository
import com.example.droidchat.model.User
import com.example.droidchat.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val chatDetailRoute = savedStateHandle.toRoute<Route.ChatDetailRoute>()

    private val sendMessageFlow = MutableSharedFlow<Unit>()

    private val _getUserUiState = MutableStateFlow<GetUserUiState>(GetUserUiState.Loading)
    val getUserUiState = _getUserUiState
        .onStart {
            getUserDetail()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = GetUserUiState.Loading
        )

    private val pagingChatMessagesState = MutableStateFlow<LoadState>(LoadState.Loading)

    private val _showError = Channel<Boolean>()
    val showError = _showError.receiveAsFlow()

    var messageText by mutableStateOf("")
        private set

    val pagingChatMessages = chatRepository.getPagedMessages(
        receiverId = chatDetailRoute.userId
    ).cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            sendMessageFlow.mapLatest {
                sendMessage()
            }.collect()
        }

        viewModelScope.launch {
            combine(
                _getUserUiState,
                pagingChatMessagesState
            ) { getUserUiState, pagingChatMessagesState ->
                Pair(getUserUiState, pagingChatMessagesState)
            }.collect {
                val (getUserUiState, pagingChatMessagesState) = it
                if (getUserUiState is GetUserUiState.Error || pagingChatMessagesState is LoadState.Error) {
                    _showError.send(true)
                }
            }
        }
    }

    fun onMessageChange(message: String) {
        messageText = message
    }

    fun onSendMessageClicked() {
        viewModelScope.launch {
            sendMessageFlow.emit(Unit)
        }
    }

    private suspend fun sendMessage() {
        chatRepository.sendMessage(
            receiverId = chatDetailRoute.userId,
            message = messageText
        )
    }

    private fun getUserDetail() {
        viewModelScope.launch {
            _getUserUiState.update {
                GetUserUiState.Loading
            }

            userRepository.getUser(chatDetailRoute.userId).fold(
                onSuccess = { user ->
                    _getUserUiState.update {
                        GetUserUiState.Success(user)
                    }
                },
                onFailure = { error ->
                    _getUserUiState.update {
                        GetUserUiState.Error(error.message ?: "Unknown error")
                    }
                }
            )
        }
    }

    fun setPagingChatMessagesLoadState(loadState: LoadState) {
        pagingChatMessagesState.update {
            loadState
        }
    }

    fun resetShowErrorState() {
        viewModelScope.launch {
            _showError.send(false)
        }
    }

    sealed interface GetUserUiState {
        data object Loading : GetUserUiState
        data class Success(val user: User) : GetUserUiState
        data class Error(val message: String) : GetUserUiState
    }
}