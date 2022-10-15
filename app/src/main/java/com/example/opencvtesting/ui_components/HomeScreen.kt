package com.example.opencvtesting.ui_components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.opencvtesting.R
import com.example.opencvtesting.viewmodel.EdgeDetectionViewModel
import java.io.File

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeScreen(
    context: Context, storageDirectory: File?, viewModel: EdgeDetectionViewModel
) {
    val originalImgBitmap = remember {
        mutableStateOf<Bitmap?>(
            BitmapFactory.decodeResource(
                context.resources, R.drawable.spidey
            )
        )
    }
    val resultImgBitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }

    Scaffold(modifier = Modifier.wrapContentSize(), floatingActionButton = {
        SaveImgFab(
            viewModel = viewModel,
            originalImgBitmap = originalImgBitmap,
            resultImgBitmap = resultImgBitmap
        )
    }, floatingActionButtonPosition = FabPosition.End, content = {
        val pad = it
        MainLayout(
            context = context,
            storageDir = storageDirectory,
            viewModel = viewModel,
            originalImgBitmap = originalImgBitmap,
            resultImgBitmap = resultImgBitmap
        )
    })
}