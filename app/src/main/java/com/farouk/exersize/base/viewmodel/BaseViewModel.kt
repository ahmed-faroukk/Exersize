package com.farouk.exersize.base.viewmodel

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.farouk.exersize.base.Connectivity.ConnectivityObserver
import com.farouk.exersize.base.Connectivity.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
): ViewModel() {



}