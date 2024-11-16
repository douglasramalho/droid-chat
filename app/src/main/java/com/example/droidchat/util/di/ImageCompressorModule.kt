package com.example.droidchat.util.di

import com.example.droidchat.util.image.ImageCompressor
import com.example.droidchat.util.image.ImageCompressorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ImageCompressorModule {

    @Binds
    fun bindImageCompressor(compressor: ImageCompressorImpl): ImageCompressor
}