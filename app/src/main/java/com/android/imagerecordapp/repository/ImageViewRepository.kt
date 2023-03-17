package com.android.imagerecordapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.data.ImageViewDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageViewRepository @Inject constructor(private val database: ImageViewDatabase) {
    fun getImageViewPagingSource(): Flow<PagingData<ImageViewData>> {
        return Pager(
            config =  PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImageViewPagingSource(database) }
        ).flow
    }
}