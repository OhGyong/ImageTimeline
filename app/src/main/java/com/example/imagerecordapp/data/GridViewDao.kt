package com.example.imagerecordapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * DAO 정의
 * 쿼리 작성
 */
@Dao
interface GridViewDao {
    @Query("SELECT * FROM gridviewdata ORDER BY date ASC")
    fun getAll(): List<GridViewData>

    @Insert
    fun insertData(gridViewData: GridViewData)

}