package com.farouk.exersize.features.home.presentaion

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farouk.exersize.features.home.domain.usecase.HomeUseCases
import com.farouk.exersize.features.home.presentaion.Packages.GetPackagesState
import com.farouk.exersize.features.home.presentaion.getAllCoaches.GetAllCoachesState
import com.farouk.exersize.features.home.presentaion.getCoach.states.GetCoachState
import com.farouk.exersize.features.home.presentaion.getCoach.states.GetPortfolioState
import com.farouk.exersize.features.home.presentaion.reqPackage.ReqPackagesState
import com.farouk.exersize.user.data.local.UserLocalDataSource
import com.farouk.exersize.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val userLocalDataSource: UserLocalDataSource
) : ViewModel() {



    private val _getAllCoaches = mutableStateOf(GetAllCoachesState())
    val getAllCoaches = _getAllCoaches

    private val _getCoach = mutableStateOf(GetCoachState())
    val getCoach = _getCoach

    private val _getPortfolio = mutableStateOf(GetPortfolioState())
    val getPortfolio = _getPortfolio

    private val _getPackages = mutableStateOf(GetPackagesState())
    val getPackages = _getPackages

    private val _reqPackages = mutableStateOf(ReqPackagesState())
    val reqPackages = _reqPackages

    private val _token : MutableState<String> = mutableStateOf("")
    val token = _token

    init {
        getToken()
        getLoggInState()
        getAllCoaches()
    }
    fun getAllCoaches() {

        homeUseCases.getAllCoachesUseCase(token = _token.value).onEach {
            //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vYWJvdmUtZWxrLW9wZW4ubmdyb2stZnJlZS5hcHAvYXBpL3RyYWluZWUvdmVyaWZ5IiwiaWF0IjoxNzEzOTg0ODg4LCJleHAiOjE3MzE5ODQ4ODgsIm5iZiI6MTcxMzk4NDg4OCwianRpIjoiOERTcE5kYlJYRVgxbzN3OCIsInN1YiI6IjIxIiwicHJ2IjoiNzAzYWFjMDNlM2JmYTYxOTYxYzlhYWUzY2IzMWFjMGExMjE4Y2JkMiJ9.8KO-85jTT5iV8IBx9iojyBoqxMvY1lRZp8ZRHFOfB_U
            when (it) {
                is Resource.Success -> {
                    _getAllCoaches.value = GetAllCoachesState(data = it.data)
                }

                is Resource.Loading -> {
                    _getAllCoaches.value = GetAllCoachesState(isLoading = true)
                }

                is Resource.Error -> {
                    _getAllCoaches.value =
                        GetAllCoachesState(error = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCoach(id : String ) {

        homeUseCases.getCoachUseCase(id = id , token =  _token.value).onEach {
            when (it) {
                is Resource.Success -> {
                    _getCoach.value = GetCoachState(data = it.data)
                }

                is Resource.Loading -> {
                    _getCoach.value = GetCoachState(isLoading = true)
                }

                is Resource.Error -> {
                    _getCoach.value = GetCoachState(error = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun getPackages(coachId : String ) {

        homeUseCases.getPackagesUseCase(id = coachId , token =  _token.value).onEach {
            when (it) {
                is Resource.Success -> {
                    _getPackages.value = GetPackagesState(data = it.data)
                }

                is Resource.Loading -> {
                    _getPackages.value = GetPackagesState(isLoading = true)
                }

                is Resource.Error -> {
                    _getPackages.value = GetPackagesState(error = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun getCoachPortfolio(coachId : String ) {

        homeUseCases.getPortfolioUseCase(id = coachId , token =  _token.value).onEach {
            when (it) {
                is Resource.Success -> {
                    _getPortfolio.value = GetPortfolioState(data = it.data)
                }

                is Resource.Loading -> {
                    _getPortfolio.value = GetPortfolioState(isLoading = true)
                }

                is Resource.Error -> {
                    _getPortfolio.value = GetPortfolioState(error = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun reqPackages(coachId : String, packageId : String ) {

        homeUseCases.reqPackageUseCase(coachId = coachId, packageId = packageId , token =  _token.value).onEach {
            when (it) {
                is Resource.Success -> {
                    _reqPackages.value = ReqPackagesState(data = it.data)
                }

                is Resource.Loading -> {
                    _reqPackages.value = ReqPackagesState(isLoading = true)
                }

                is Resource.Error -> {
                    _reqPackages.value = ReqPackagesState(error = it.message ?: "error not found")
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


     fun getLoggInState(){
        viewModelScope.launch {
            userLocalDataSource.isLoggedIn().onEach {
                println("---------------------------------------------------------is logged in $it")
            }.launchIn(viewModelScope)
        }
    }


    }



