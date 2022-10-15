package com.example.opencvtesting.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.opencvtesting.models.StoredImage
import com.example.opencvtesting.typeconverters.Converter

@Database(entities = [(StoredImage::class)], version = 1)
@TypeConverters(Converter::class)
abstract class ImageDatabase: RoomDatabase() {
    abstract fun getDao():ImageDao

        companion object {
            fun create(context: Context): ImageDatabase {

                return Room.databaseBuilder(
                    context,
                    ImageDatabase::class.java,
                    "storedImg_db"
                ).build()
            }
        }
}