package com.example.droidchat.ui.feature.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.droidchat.data.repository.ChatRepository
import com.example.droidchat.model.Chat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
) : ViewModel() {

    private val _chatsListUiState = MutableStateFlow<ChatsListUiState>(ChatsListUiState.Loading)
    val chatsListUiState = _chatsListUiState.asStateFlow()

    init {
        getChats()
    }

    fun getChats() {
        viewModelScope.launch {
            _chatsListUiState.update {
                ChatsListUiState.Loading
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