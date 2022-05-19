package com.mitm.android.grocerysharedlist.presentation.ui.room_settings.composable

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardQR(
    onClick: (() -> Unit)? = null,
    size: Dp = 150.dp,
    bitmap: ImageBitmap){
    Card(
        modifier = Modifier.size(size),
        elevation = 2.dp
    ) {
        Image(
            bitmap = bitmap,
            contentDescription = "image_qr",
            modifier = Modifier.fillMaxSize()
                .clickable {
                    onClick?.invoke()
                }
        )
    }
}