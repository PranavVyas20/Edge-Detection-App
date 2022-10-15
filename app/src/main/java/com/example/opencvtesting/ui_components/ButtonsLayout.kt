package com.example.opencvtesting.ui_components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.FileProvider
import com.example.opencvtesting.viewmodel.EdgeDetectionViewModel
import java.io.File

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ButtonsLayout(
    context: Context,
    storageDirectory: File?,
    viewModel: EdgeDetectionViewModel,
    originalImgBitmap: MutableState<Bitmap?>,
    resultImgBitmap: MutableState<Bitmap?>,
    urlTextFieldState: MutableState<Boolean>
) {
    val imagePath = remember { mutableStateOf("") }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            originalImgBitmap.value = BitmapFactory.decodeFile(imagePath.value)
            originalImgBitmap.value?.let { orgBtmp ->
                viewModel.convertImage(
                    orgBtmp, resultImgBitmap
                )
            }
        }
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { selectedImgUri ->
        selectedImgUri?.let {
            val src = ImageDecoder.createSource(context.contentResolver, it)
            originalImgBitmap.value = ImageDecoder.decodeBitmap(src) { decoder, _, _ ->
                decoder.isMutableRequired = true
            }
            originalImgBitmap.value?.let { orgBtmp ->
                viewModel.convertImage(
                    orgBtmp, resultImgBitmap
                )
            }
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Button(onClick = {
            val imageFile: File = File.createTempFile("photo", ".jpg", storageDirectory)
            imagePath.value = imageFile.absolutePath
            val imageUri = FileProvider.getUriForFile(
                context, "com.example.opencvtesting.fileprovider", imageFile
            )
            cameraLauncher.launch(imageUri)

            if (urlTextFieldState.value) {
                urlTextFieldState.value = false
            }
        }) {
            Text(text = "Camera")
        }
        Button(onClick = {
            galleryLauncher.launch("image/*")
            if (urlTextFieldState.value) {
                urlTextFieldState.value = false
            }
        }) {
            Text(text = "Gallery")
        }
        Button(onClick = {
            urlTextFieldState.value = !urlTextFieldState.value
        }) {
            Text(text = "Url")
        }
    }
}