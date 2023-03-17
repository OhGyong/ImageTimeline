package com.android.imagerecordapp.di

import android.content.Context
import androidx.room.Room
import com.android.imagerecordapp.data.ImageViewDao
import com.android.imagerecordapp.data.ImageViewDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideDao(database: ImageViewDatabase) : ImageViewDao {
        return database.imageViewDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            ImageViewDatabase::class.java, "image_record"
        ).build()
}