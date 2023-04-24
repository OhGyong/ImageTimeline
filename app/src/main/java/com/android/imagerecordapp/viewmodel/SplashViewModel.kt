package com.android.imagerecordapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading

    init {
        viewModelScope.launch {
            delay(1200L)
            _isLoading.value = false
        }
    }
}