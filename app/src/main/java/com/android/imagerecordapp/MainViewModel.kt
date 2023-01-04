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
    val listSize: MutableLiveData<Int> = MutableLiveData()
    val insertObserve: MutableLiveData<Unit> = MutableLiveData()
    val deleteObserve: MutableLiveData<Unit> = MutableLiveData()


    fun getImageListData(db: GridViewDatabase, page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                imageList.postValue(db.gridViewDao().getAll(page))
            }catch (error: Exception){
                println("이미지 불러오기 실패 $error")
            }
        }
    }

    // 데이터 베이스에 사진 정보 저장
    fun inputImageData(db: GridViewDatabase, date: String, uri: String ){
        viewModelScope.launch(Dispatchers.IO){
            insertObserve.postValue(db.gridViewDao().insertData(GridViewData(date, uri)))
        }
    }

    // 사진 삭제
    fun deleteImageData(db: GridViewDatabase, uri: String){
        viewModelScope.launch(Dispatchers.IO) {
            deleteObserve.postValue(db.gridViewDao().deleteData(uri))
        }
    }

    fun getListSizeData(db: GridViewDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            listSize.postValue(db.gridViewDao().getListSize())
        }
    }
}