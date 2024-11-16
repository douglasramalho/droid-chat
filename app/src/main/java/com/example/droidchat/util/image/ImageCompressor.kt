package com.example.droidchat.util.image

import android.net.Uri
import java.io.File

interface ImageCompressor {

    suspend fun compressAndResizeImage(
        imageUri: Uri,
        quality: Int = 80,
        maxWidth: Int = 1080,
        maxHeight: Int = 1080,
    ): File
}