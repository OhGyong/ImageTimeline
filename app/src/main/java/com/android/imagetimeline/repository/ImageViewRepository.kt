package com.android.imagetimeline.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.imagetimeline.data.BaseResult
import com.android.imagetimeline.data.ImageViewDao
import com.android.imagetimeline.data.ImageViewData
import com.android.imagetimeline.data.ImageViewDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageViewRepository @Inject constructor(
    private val database: ImageViewDatabase,
    private val dao: ImageViewDao
    ) {
    fun getImageViewPagingSource(): Flow<PagingData<ImageViewData>> {
        return Pager(
            config =  PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImageViewPagingSource(database) }
        ).flow
    }

    suspend fun inputImageData(date: String, uri: String): BaseResult {
        val baseResult = BaseResult()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                baseResult.success = dao.insertImage(ImageViewData(date, uri))
            }catch (e: Exception) {
                baseResult.failure = e
            }
        }.join()
        return baseResult
    }

    suspend fun deleteImageData(uri: String): BaseResult {
        val baseResult = BaseResult()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                baseResult.success = dao.deleteImage(uri)
            }catch (e: Exception) {
                baseResult.failure = e
            }
        }.join()
        return baseResult
    }
}