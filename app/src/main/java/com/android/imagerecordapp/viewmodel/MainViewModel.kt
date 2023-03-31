package com.android.imagerecordapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.android.imagerecordapp.data.BaseResult
import com.android.imagerecordapp.data.ImageViewData
import com.android.imagerecordapp.repository.ImageViewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var imageViewRepository: ImageViewRepository) : ViewModel() {

    private val _insertObserve: MutableLiveData<BaseResult> = MutableLiveData()
    val insertObserve = _insertObserve

    private val _deleteObserve: MutableLiveData<BaseResult> = MutableLiveData()
    val deleteObserve = _deleteObserve

    fun getImageListData() : Flow<PagingData<ImageViewData>> {
        return imageViewRepository.getImageViewPagingSource().buffer(128).cachedIn(viewModelScope)
    }

    // 데이터 베이스에 사진 정보 저장
    fun inputImageData(date: String, uri: String) {
        viewModelScope.launch(Dispatchers.IO){
            _insertObserve.postValue(imageViewRepository.inputImageData(date, uri))
        }
    }

    // 사진 삭제
    fun deleteImageData(uri: String){
        viewModelScope.launch(Dispatchers.IO) {
            _deleteObserve.postValue(imageViewRepository.deleteImageData(uri))
        }
    }
}