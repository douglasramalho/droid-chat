package com.example.droidchat.ui.feature.users

import androidx.lifecycle.ViewModel
import com.example.droidchat.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    userRepository: UserRepository,
) : ViewModel() {

    val usersFlow = userRepository.getUsers()
}