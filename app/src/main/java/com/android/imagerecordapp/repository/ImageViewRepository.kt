package com.android.imagerecordapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.imagerecordapp.data.ImageViewData
import kotlinx.coroutines.flow.Flow

class ImageViewRepository {
    fun getImageViewPagingSource(): Flow<PagingData<ImageViewData>> {
        return Pager(
            config =  PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImageViewPagingSource() }
        ).flow
    }
}