package com.android.imagerecordapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.data.ImageViewDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val imageList: MutableLiveData<List<ImageViewData>> = MutableLiveData()
    val listSize: MutableLiveData<Int> = MutableLiveData()
    val insertObserve: MutableLiveData<Unit> = MutableLiveData()
    val deleteObserve: MutableLiveData<Unit> = MutableLiveData()


    fun getImageListData(db: ImageViewDatabase, page: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try{
                imageList.postValue(db.imageViewDao().getAll(page))
            }catch (error: Exception){
                println("이미지 불러오기 실패 $error")
            }
        }
    }

    // 데이터 베이스에 사진 정보 저장
    fun inputImageData(db: ImageViewDatabase, date: String, uri: String ){
        viewModelScope.launch(Dispatchers.IO){
            insertObserve.postValue(db.imageViewDao().insertData(ImageViewData(date, uri)))
        }
    }

    // 사진 삭제
    fun deleteImageData(db: ImageViewDatabase, uri: String){
        viewModelScope.launch(Dispatchers.IO) {
            deleteObserve.postValue(db.imageViewDao().deleteData(uri))
        }
    }

    fun getListSizeData(db: ImageViewDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            listSize.postValue(db.imageViewDao().getListSize())
        }
    }
}