package com.example.opencvtesting.ui_components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun OrgEditedImageLayout(
    originalImgBitmap: MutableState<Bitmap?>, edgeDetectedImgBitmap: MutableState<Bitmap?>
) {
    Column {
        originalImgBitmap.value?.let { imgBitmap ->
            Image(
                bitmap = imgBitmap.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        edgeDetectedImgBitmap.value?.let { imgBitmap ->
            Image(
                bitmap = imgBitmap.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
        }
    }
}