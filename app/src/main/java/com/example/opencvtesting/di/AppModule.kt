package com.example.opencvtesting.di

import android.content.Context
import com.example.opencvtesting.db.ImageDao
import com.example.opencvtesting.db.ImageDatabase
import com.example.opencvtesting.repository.EdgeDetectionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ImageDatabase =
        ImageDatabase.create(context)

    @Provides
    fun provideDao(database: ImageDatabase): ImageDao {
        return database.getDao()
    }

    @Provides
    fun provideRepository(storedImgDao: ImageDao): EdgeDetectionRepository{
        return EdgeDetectionRepository(storedImgDao)
    }
}