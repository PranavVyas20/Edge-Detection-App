package com.example.opencvtesting.models

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savedImages_table")
data class StoredImage(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val originalImgBitmap: Bitmap,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val resultImgBitmap: Bitmap
)
