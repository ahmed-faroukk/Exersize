package com.farouk.exersize.features.reports.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farouk.exersize.user.data.local.UserLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(val userLocalDataSource: UserLocalDataSource) :
    ViewModel() {
    private val _userId: MutableState<String> = mutableStateOf("")
    val userId = _userId

    init {
        getUserData()
    }
    fun getUserData() {
        viewModelScope.launch {
            userLocalDataSource.getUserId().onEach { userId ->
                _userId.value = userId.toString()
                println("---------------------------------------------------------userid from chat vm")
                println("$userId")
            }.launchIn(viewModelScope)
        }
    }
}