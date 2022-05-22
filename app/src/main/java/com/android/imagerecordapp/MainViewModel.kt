package com.android.imagerecordapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.imagerecordapp.data.GridViewData
import com.android.imagerecordapp.data.GridViewDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URI

@DelicateCoroutinesApi
class MainViewModel: ViewModel() {
    val imageList: MutableLiveData<List<GridViewData>> = MutableLiveData()

    fun getImageListData(db: GridViewDatabase){
        GlobalScope.launch(Dispatchers.IO){
            try{
                imageList.postValue(db.gridViewDao().getAll())
            }catch (error: Exception){
                println("이미지 불러오기 실패 $error")
            }
        }
    }

    // 데이터 베이스에 사진 정보 저장
    fun inputImageData(db: GridViewDatabase, date: String, uri: String ){
        GlobalScope.launch(Dispatchers.IO){
            db.gridViewDao().insertData(GridViewData(date, uri)) // 데이터 삽입
        }
    }

    // 사진 삭제
    fun deleteImageData(db: GridViewDatabase, uri: String){
        GlobalScope.launch {
            db.gridViewDao().deleteData(uri) // 데이터 삭제
        }
    }
}