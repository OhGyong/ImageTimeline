package com.android.imagerecordapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.data.ImageViewDatabase
import com.android.imagerecordapp.repository.ImageViewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val insertObserve: MutableLiveData<Unit> = MutableLiveData()
    val deleteObserve: MutableLiveData<Unit> = MutableLiveData()


    fun getImageListData() : Flow<PagingData<ImageViewData>> {
        return ImageViewRepository().getImageViewPagingSource().cachedIn(viewModelScope)
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
}