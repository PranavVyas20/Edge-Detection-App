package com.example.opencvtesting.typeconverters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converter {
    @TypeConverter
    fun convertToByteArray(btmp:Bitmap) : ByteArray {
        val outputStream = ByteArrayOutputStream()
        btmp.compress(Bitmap.CompressFormat.PNG,50, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun convertToBitmap(byteArray: ByteArray) : Bitmap {
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }
}