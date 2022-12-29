package com.android.imagerecordapp.data

import androidx.room.*

/**
 * DAO 정의
 * 쿼리 작성
 */
@Dao
interface GridViewDao {
    @Query("SELECT * FROM gridviewdata ORDER BY date ASC")
    fun getAll(): List<GridViewData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(gridViewData: GridViewData) : Long
//    @Query("INSERT INTO gridviewdata VALUES(:date, :uri)")
//    fun insertData(date: String, uri: String)

    @Query("DELETE FROM gridviewdata WHERE img_uri = :imgUri")
    fun deleteData(imgUri: String)
}