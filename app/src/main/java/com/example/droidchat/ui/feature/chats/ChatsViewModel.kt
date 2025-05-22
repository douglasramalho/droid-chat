package com.example.droidchat.ui.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.data.repository.AuthRepository
import com.example.droidchat.data.repository.ChatRepository
import com.example.droidchat.model.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    authRepository: AuthRepository,
) : ViewModel() {

    private val _chatsListUiState = MutableStateFlow<ChatsListUiState>(ChatsListUiState.Loading)
    val chatsListUiState = _chatsListUiState
        .onStart {
            getChats()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ChatsListUiState.Loading
        )

    val currentUserFlow = authRepository.currentUserFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            chatRepository.newMessageReceivedFlow.collect {
                getChats()
            }
        }
    }

    fun getChats(isRefresh: Boolean = false) {
        viewModelScope.launch {
            if (isRefresh) {
                _chatsListUiState.update {
                    ChatsListUiState.Loading
                }
            }

            chatRepository.getChats(
                offset = 0,
                limit = 10,
            ).fold(
                onSuccess = { chats ->
                    _chatsListUiState.update {
                        ChatsListUiState.Success(chats)
                    }
                },
                onFailure = {
                    _chatsListUiState.update {
                        ChatsListUiState.Error
                    }
                }
            )
        }
    }

    sealed interface ChatsListUiState {
        data object Loading : ChatsListUiState
        data class Success(val chats: List<Chat>) : ChatsListUiState
        data object Error : ChatsListUiState
    }
}