package com.mentalhealth.eifie.ui.common.photo

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.mentalhealth.eifie.ui.common.imagecontainer.CircularEmptyPhoto
import com.mentalhealth.eifie.util.getInitials

@Composable
fun UserPhotoView(
    uri: Any? = null,
    username: String? = null,
    fontSize: TextUnit = 40.sp,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    if(uri != null) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            onState = {
                when(it) {
                    AsyncImagePainter.State.Empty -> Unit
                    is AsyncImagePainter.State.Loading -> {
                        Log.d("IMAGE_STATUS", "LOADING")
                    }
                    is AsyncImagePainter.State.Success -> {
                        Log.d("IMAGE_STATUS", "SUCCESS")
                    }
                    is AsyncImagePainter.State.Error -> it.run {
                        Log.e("IMAGE_STATUS", "ERROR", result.throwable)
                    }
                }
            },
            contentScale = ContentScale.Crop,
            modifier = modifier
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = CircleShape
                )
                .clip(CircleShape)
        )
    } else {
        CircularEmptyPhoto(
            text = username.getInitials(),
            fontSize = fontSize,
            modifier = modifier
        )
    }
}