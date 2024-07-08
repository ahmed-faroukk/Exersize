package com.farouk.exersize.features.menu

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.farouk.exersize.features.menu.profile.domain.usecases.ProfileUseCases
import com.farouk.exersize.features.menu.profile.presentaion.UpdateProfileState
import com.farouk.exersize.features.onBoarding.OnboardingScreen
import com.farouk.exersize.user.data.local.UserLocalDataSource
import com.farouk.exersize.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val user: UserLocalDataSource,
    private val profileUseCase : ProfileUseCases
    ) : ViewModel() {

    fun updateUserData(navigator: Navigator) {
        viewModelScope.launch {
            user.setLoggedout()
            navigator.replaceAll(OnboardingScreen())
        }
    }
    private val _updateProfileState  = mutableStateOf(UpdateProfileState())
    val updateProfileState = _updateProfileState

    fun getAllCoaches(
        fname: RequestBody,
        lname: RequestBody,
        phone: RequestBody,
        img: MultipartBody.Part,
        token: RequestBody,
    ) {

        profileUseCase.updateProfileUseCase(fname, lname, phone, img, token).onEach {
            //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vYWJvdmUtZWxrLW9wZW4ubmdyb2stZnJlZS5hcHAvYXBpL3RyYWluZWUvdmVyaWZ5IiwiaWF0IjoxNzEzOTg0ODg4LCJleHAiOjE3MzE5ODQ4ODgsIm5iZiI6MTcxMzk4NDg4OCwianRpIjoiOERTcE5kYlJYRVgxbzN3OCIsInN1YiI6IjIxIiwicHJ2IjoiNzAzYWFjMDNlM2JmYTYxOTYxYzlhYWUzY2IzMWFjMGExMjE4Y2JkMiJ9.8KO-85jTT5iV8IBx9iojyBoqxMvY1lRZp8ZRHFOfB_U
            when (it) {
                is Resource.Success -> {
                    _updateProfileState.value = UpdateProfileState(data = it.data)
                }

                is Resource.Loading -> {
                    _updateProfileState.value = UpdateProfileState(isLoading = true)
                }

                is Resource.Error -> {
                    _updateProfileState.value = UpdateProfileState(error = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)
    }
}