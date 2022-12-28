package com.android.imagerecordapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.imagerecordapp.data.GridViewData
import com.android.imagerecordapp.data.GridViewDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val imageList: MutableLiveData<List<GridViewData>> = MutableLiveData()

    fun getImageListData(db: GridViewDatabase){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                imageList.postValue(db.gridViewDao().getAll())
            }catch (error: Exception){
                println("이미지 불러오기 실패 $error")
            }
        }
    }

    // 데이터 베이스에 사진 정보 저장
    fun inputImageData(db: GridViewDatabase, date: String, uri: String ){
        viewModelScope.launch(Dispatchers.IO){
            db.gridViewDao().insertData(GridViewData(date, uri)) // 데이터 삽입
            getImageListData(db)
        }
    }

    // 사진 삭제
    fun deleteImageData(db: GridViewDatabase, uri: String){
        viewModelScope.launch {
            db.gridViewDao().deleteData(uri) // 데이터 삭제
            getImageListData(db)
        }
    }
}