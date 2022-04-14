package com.example.imagerecordapp.data

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * DB Entity 정의 (DB에 저장할 데이터 정의)
 */
@Entity
data class GridViewData(
    @NonNull @ColumnInfo(name = "date") val date: String,
    @NonNull @ColumnInfo(name = "img_uri") var imgUri: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}


data class ViewData(
    val date: String,
    var imgUri: Uri
)