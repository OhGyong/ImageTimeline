package com.android.imagerecordapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.android.imagerecordapp.data.ImageViewDao
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.data.ImageViewDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ImageDaoTest {
    private lateinit var imageDb: ImageViewDatabase
    private lateinit var imageDao: ImageViewDao

    @Before
    fun openDb() {
        imageDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ImageViewDatabase::class.java
        ).allowMainThreadQueries().build()
        imageDao = imageDb.imageViewDao()
    }

    @After
    fun closeDb() {
        imageDb.close()
    }

    @Test
    fun selectTest() {
        runBlocking {
            imageDao.selectAll(1)
        }
    }

    @Test
    fun insertTest() = runBlocking {
        val imageData = ImageViewData("2022-12-12", "uri")
        imageDao.insertImage(imageData)
    }

    @Test
    fun deleteTest() {
        runBlocking {
            imageDao.deleteImage("uri")
        }
    }
}