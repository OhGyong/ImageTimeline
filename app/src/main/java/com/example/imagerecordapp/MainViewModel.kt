package com.example.imagerecordapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagerecordapp.data.GridViewData
import com.example.imagerecordapp.data.GridViewDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
}