package com.farouk.exersize.features.splash.presentaiton

import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.farouk.exersize.R
import com.farouk.exersize.base.animations.AFAnimateAsFloat
import com.farouk.exersize.features.splash.presentaiton.components.ShowLogo

object SplashScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: SplashViewModel = getViewModel()
        SplashScreen(viewModel)
    }

    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun SplashScreen(
         viewModel: SplashViewModel
    ) {
        val navigator = LocalNavigator.currentOrThrow
        viewModel.navigateTo(navigator)
        AFAnimateAsFloat(
            delay = 500,
            initValue = 400f,
            targetValue = 300f,
            animationSpec = tween(100),
            content = { modifier, isVisible ->
                ShowLogo(modifier = modifier, R.drawable.sec_logo, 300f)
                if (isVisible.value)
                    ShowLogo(
                        modifier = modifier.padding(bottom = 80.dp),
                        R.drawable.main_logo,
                        400f
                    )
            } )


    }


}

