package com.android.imagerecordapp.data

import androidx.room.*

/**
 * DAO 정의
 * 쿼리 작성
 */
@Dao
interface ImageViewDao {
    @Query("SELECT * FROM imageviewdata ORDER BY date ASC LIMIT 10 OFFSET (:page-1)*10")
    fun getAll(page: Int): List<ImageViewData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(imageViewData: ImageViewData)
//    @Query("INSERT INTO imageviewdata VALUES(:date, :uri)")
//    fun insertData(date: String, uri: String)

    @Query("DELETE FROM imageviewdata WHERE img_uri = :imgUri")
    fun deleteData(imgUri: String)

    @Query("SELECT COUNT(*) FROM imageviewdata")
    fun getListSize(): Int
}