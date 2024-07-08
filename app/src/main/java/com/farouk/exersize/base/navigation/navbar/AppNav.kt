package com.farouk.exersize.base.navigation.navbar

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.farouk.exersize.features.splash.presentaiton.SplashScreen

@Composable
fun AppNav(){
    Navigator(
        screen = SplashScreen,
        content = { navigator ->
            SlideTransition(navigator)
        }
    )
}