package com.android.imagerecordapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageViewData::class], version = 1)
abstract class ImageViewDatabase : RoomDatabase() {
    abstract fun imageViewDao() : ImageViewDao

    companion object {
        var imageViewDB: ImageViewDatabase? = null

        fun getDbInstance(context: Context): ImageViewDatabase =
            imageViewDB ?: synchronized(this) {
                imageViewDB ?: buildDatabase(context).also { imageViewDB = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ImageViewDatabase::class.java, "image_record"
            ).build()
    }
}