package com.example.opencvtesting.repository

import com.example.opencvtesting.db.ImageDao
import com.example.opencvtesting.models.StoredImage

class EdgeDetectionRepository(private val imageDao: ImageDao) {

    fun saveImage(storedImage: StoredImage) {
        imageDao.saveImages(storedImage)
    }
    fun getStoredImages() = imageDao.getSavedImages()

}