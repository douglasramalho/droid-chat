package com.example.droidchat.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.droidchat.R
import com.example.droidchat.ui.theme.DroidChatTheme

@Composable
fun ProfilePictureSelector(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    isCompressingImage: Boolean,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = imageUri ?: R.drawable.ic_upload_photo,
                contentDescription = null,
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape),
                placeholder = painterResource(id = R.drawable.ic_upload_photo),
                contentScale = ContentScale.Crop,
            )

            if (isCompressingImage) {
                CircularProgressIndicator()
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        val text = if (isCompressingImage) {
            R.string.common_add_profile_photo_optimizing
        } else R.string.common_add_profile_photo
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePictureSelectorPreview() {
    DroidChatTheme {
        ProfilePictureSelector(
            isCompressingImage = false,
        )
    }
}