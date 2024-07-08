package com.farouk.exersize.features.splash.presentaiton

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.farouk.exersize.base.navigation.navbar.NavBarContainer
import com.farouk.exersize.features.onBoarding.OnboardingScreen
import com.farouk.exersize.user.data.local.UserLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val userLocalDataSource: UserLocalDataSource,
) : ViewModel() {

    val userStateIsLoggedIn = mutableStateOf(false)
    val doAction = mutableStateOf(true)

    init {
        isUSerLoggedIn()
    }

    fun navigateTo(navigator: Navigator) {
        viewModelScope.launch {
            delay(3000)
        }.invokeOnCompletion {
            println("-----------------------from splsh vm is logged in ${userStateIsLoggedIn.value}")
            if (userStateIsLoggedIn.value && doAction.value) {
                navigateToHome(navigator)
                doAction.value = false
            } else if (!userStateIsLoggedIn.value && doAction.value) {
                navigateToOnBoarding(navigator)
                doAction.value = false
            }
        }
    }


    fun isUSerLoggedIn() {
        viewModelScope.launch {
            userLocalDataSource.isLoggedIn().onEach {
                userStateIsLoggedIn.value = it
            }.launchIn(viewModelScope)
        }

    }

    private fun navigateToOnBoarding(navigator: Navigator) {
        navigator.replace(OnboardingScreen())
    }

    fun navigateToHome(navigator: Navigator) {
        navigator.replace(NavBarContainer())
    }
}