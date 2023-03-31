package com.android.imagerecordapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.imagerecordapp.data.ImageViewDao
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.repository.ImageViewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dao: ImageViewDao) : ViewModel() {
    @Inject lateinit var imageViewRepository: ImageViewRepository

    private val _insertObserve: MutableLiveData<Unit> = MutableLiveData()
    val insertObserve = _insertObserve

    private val _deleteObserve: MutableLiveData<Unit> = MutableLiveData()
    val deleteObserve = _deleteObserve

    fun getImageListData() : Flow<PagingData<ImageViewData>> {
        return imageViewRepository.getImageViewPagingSource().cachedIn(viewModelScope)
    }

    // 데이터 베이스에 사진 정보 저장
    fun inputImageData(date: String, uri: String ){
        viewModelScope.launch(Dispatchers.IO){
            _insertObserve.postValue(dao.insertData(ImageViewData(date, uri)))
        }
    }

    // 사진 삭제
    fun deleteImageData(uri: String){
        viewModelScope.launch(Dispatchers.IO) {
            _deleteObserve.postValue(dao.deleteData(uri))
        }
    }
}