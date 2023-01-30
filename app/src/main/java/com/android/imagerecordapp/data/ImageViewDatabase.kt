package com.android.imagerecordapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ImageViewData::class], version = 1)
abstract class ImageViewDatabase : RoomDatabase() {
    abstract fun imageViewDao() : ImageViewDao
}