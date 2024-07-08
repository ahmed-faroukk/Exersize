package com.farouk.exersize.features.plan.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farouk.exersize.features.plan.domain.usecase.GetTraineePlanUseCase
import com.farouk.exersize.user.data.local.UserLocalDataSource
import com.farouk.exersize.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlansViewModel @Inject constructor(
    private val useCase : GetTraineePlanUseCase ,
    private val userLocalDataSource: UserLocalDataSource,
): ViewModel() {

   private val _traineePlanState = mutableStateOf(TraineePlanState())
    val traineePlanState = _traineePlanState

    private val _token : MutableState<String> = mutableStateOf("")
    val token = _token
    init {
        getToken()
        getTraineePlans()
    }

    fun getTraineePlans()  {
        useCase.invoke(_token.value).onEach {
            when (it) {
                is Resource.Success -> {
                    traineePlanState.value = TraineePlanState(data = it.data)
                }

                is Resource.Loading -> {
                    traineePlanState.value = TraineePlanState(isLoading = true)
                }

                is Resource.Error -> {
                    traineePlanState.value =
                        TraineePlanState(errorMsg = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getToken() {
        viewModelScope.launch {
            userLocalDataSource.getToken().onEach {token->
                _token.value = token.toString()
                println("---------------------------------------------------------token from home vm")
                println("$token")
            }.launchIn(viewModelScope)
        }
    }

}