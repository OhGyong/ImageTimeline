package com.android.imagerecordapp.data

import androidx.room.*

/**
 * DAO 정의
 * 쿼리 작성
 */
@Dao
interface ImageViewDao {
    @Query("SELECT * FROM imageviewdata ORDER BY date ASC LIMIT 10 OFFSET (:page-1)*10")
    suspend fun selectAll(page: Int): List<ImageViewData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageViewData: ImageViewData)
//    @Query("INSERT INTO imageviewdata VALUES(:date, :uri)")
//    fun insertData(date: String, uri: String)

    @Query("DELETE FROM imageviewdata WHERE img_uri = :imgUri")
    suspend fun deleteImage(imgUri: String)
}