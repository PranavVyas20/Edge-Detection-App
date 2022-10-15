package com.example.opencvtesting.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.opencvtesting.models.StoredImage
import kotlinx.coroutines.flow.Flow
@Dao
interface ImageDao {
    @Insert
     fun saveImages(storedImage: StoredImage)

    @Delete
     fun delete(storedImage: StoredImage)

    @Query("SELECT * FROM savedImages_table")
     fun getSavedImages(): Flow<List<StoredImage>>
}