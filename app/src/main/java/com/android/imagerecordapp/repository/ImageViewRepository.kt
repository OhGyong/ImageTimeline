package com.android.imagerecordapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.imagerecordapp.data.ImageViewData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageViewRepository @Inject constructor(private val imageViewPagingSource: ImageViewPagingSource) {
    fun getImageViewPagingSource(): Flow<PagingData<ImageViewData>> {
        return Pager(
            config =  PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { imageViewPagingSource }
        ).flow
    }
}