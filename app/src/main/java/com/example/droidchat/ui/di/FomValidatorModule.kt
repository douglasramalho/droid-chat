package com.example.droidchat.ui.di

import com.example.droidchat.ui.feature.signup.SignUpFormState
import com.example.droidchat.ui.feature.signup.SignUpFormValidator
import com.example.droidchat.ui.validator.FormValidator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface FomValidatorModule {

    @Binds
    fun bindSignUpFormValidator(
        signUpFormValidator: SignUpFormValidator
    ): FormValidator<SignUpFormState>

}