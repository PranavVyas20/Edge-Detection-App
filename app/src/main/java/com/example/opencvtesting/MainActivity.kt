package com.example.opencvtesting

import android.database.CursorWindow
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.opencvtesting.ui.theme.OpenCVTestingTheme
import com.example.opencvtesting.ui_components.HomeScreen
import com.example.opencvtesting.viewmodel.EdgeDetectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.OpenCVLoader
import java.io.File
import java.lang.reflect.Field

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        OpenCVLoader.initDebug()
        setContent {
            val viewModel = hiltViewModel<EdgeDetectionViewModel>()
            OpenCVTestingTheme {
                val context = LocalContext.current
                val storageDirectory: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                HomeScreen(
                    context = context,
                    storageDirectory = storageDirectory,
                    viewModel = viewModel
                )
            }
        }
    }
}










