package com.example.opencvtesting.ui_components
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.opencvtesting.models.StoredImage
import com.example.opencvtesting.ui.theme.Purple200
import com.example.opencvtesting.viewmodel.EdgeDetectionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xdroid.toaster.Toaster
import java.io.File
import java.net.URL

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MainLayout(
    context: Context,
    storageDir: File?,
    originalImgBitmap: MutableState<Bitmap?>,
    resultImgBitmap: MutableState<Bitmap?>,
    viewModel: EdgeDetectionViewModel
) {
    val urlTextFieldState = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.convertImage(originalImgBitmap.value!!, resultImgBitmap)
    }
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            ButtonsLayout(
                context = context,
                storageDirectory = storageDir,
                viewModel = viewModel,
                originalImgBitmap = originalImgBitmap,
                resultImgBitmap = resultImgBitmap,
                urlTextFieldState = urlTextFieldState
            )
            AnimatedVisibility(visible = urlTextFieldState.value) {
                UrlTextField(viewModel, originalImgBitmap, resultImgBitmap)
            }
            OrgEditedImageLayout(
                originalImgBitmap = originalImgBitmap, edgeDetectedImgBitmap = resultImgBitmap
            )
        }
    }
}
@Composable
fun SaveImgFab(
    viewModel: EdgeDetectionViewModel,
    originalImgBitmap: MutableState<Bitmap?>,
    resultImgBitmap: MutableState<Bitmap?>
) {
    FloatingActionButton(
        onClick = {
            val orgResImg = StoredImage(
                originalImgBitmap = originalImgBitmap.value!!,
                resultImgBitmap = resultImgBitmap.value!!
            )
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.saveImageToDb(orgResImg)
            }
        }, backgroundColor = Purple200
    ) {
        Text(text = "Save")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UrlTextField(
    viewModel: EdgeDetectionViewModel,
    originalImgBitmap: MutableState<Bitmap?>,
    resultImgBitmap: MutableState<Bitmap?>
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val text = remember { mutableStateOf("") }
    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .fillMaxWidth()
            .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 5.dp),
        placeholder = { Text(text = "Enter image url")},
        singleLine = true,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                try {
                    val url = URL(text.value)
                    text.value = ""
                    viewModel.getBitmapFromUrl(
                        url, originalImgBitmap, resultImgBitmap
                    )
                } catch (e: Exception) {
                    Toaster.toast("Invalid url")
                }
            },
        )
    )
}

