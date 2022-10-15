package com.example.opencvtesting.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.opencvtesting.models.StoredImage
import com.example.opencvtesting.repository.EdgeDetectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import xdroid.toaster.Toaster.toast
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class EdgeDetectionViewModel @Inject constructor(
    private val edgeDetectionRepository: EdgeDetectionRepository
): ViewModel() {
    fun getBitmapFromUrl(
        url: URL,
        orgBtmpImg: MutableState<Bitmap?>,
        resultImgBitmap: MutableState<Bitmap?>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                orgBtmpImg.value = BitmapFactory.decodeStream(withContext(Dispatchers.IO) {
                    url.openStream()
                })
                convertImage(orgBtmpImg.value!!, resultImgBitmap)
            } catch (e: Exception) {
                toast("error loading image")
                Log.d("urlImg", e.toString())
            }
        }
    }

    fun convertImage(
        originalImage: Bitmap, resultImage: MutableState<Bitmap?>
    ) {
        val rgba = Mat()
        Utils.bitmapToMat(originalImage, rgba)

        val edges = Mat(rgba.size(), CvType.CV_8UC1)
        Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_RGB2GRAY, 4)
        Imgproc.Canny(edges, edges, 40.0, 80.0)

        resultImage.value = Bitmap.createBitmap(edges.cols(), edges.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(edges, resultImage.value)
    }

    fun saveImageToDb(storedImage: StoredImage) {
        try {
            edgeDetectionRepository.saveImage(storedImage)
            toast("Saved")
        }catch (e:Exception) {
            toast("Error saving")
            Log.d("storedImage",e.toString())
        }
    }
    fun getSavedImages() {
        edgeDetectionRepository.getStoredImages().onEach {
            Log.d("storedImageList",it.toString())
        }.launchIn(viewModelScope)
    }

}