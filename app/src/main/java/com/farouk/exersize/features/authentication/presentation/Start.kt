package com.farouk.exersize.features.authentication.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.presentation.components.AuthText
import com.farouk.exersize.features.authentication.presentation.components.RoundedBtn
import com.farouk.exersize.features.authentication.presentation.login.LoginScreen
import com.farouk.exersize.features.authentication.presentation.signup.SignupScreen
import com.farouk.exersize.theme.blue2
import kotlinx.coroutines.delay

class AuthStart() : Screen {
    @Composable
    override fun Content() {
        Start()
    }

}

@Composable
fun Start() {
    val navigator = LocalNavigator.currentOrThrow

    val isVisible: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val width = remember {
        mutableStateOf(445)
    }
    val height = remember {
        mutableStateOf(400)
    }

    val targetValue = remember { mutableStateOf(390f) }

    println(isVisible.value)

    LaunchedEffect(isVisible.value) {
        // Hide the box for 200 milliseconds
        if (!isVisible.value) {
            delay(1)
            isVisible.value = true
        }
        delay(500)
    }

    LazyColumn(
        Modifier
            .background(blue2)
            .fillMaxWidth()
            .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {


        item {
            val size by animateFloatAsState(
                targetValue = if (!isVisible.value) 270f else targetValue.value,
                label = "", animationSpec = tween(500)
            )

            Box(
                Modifier
                    .height(440.dp)
                    .width(450.dp)
            ) {
                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth(),
                    visible = isVisible.value,
                    enter = slideInHorizontally(
                        initialOffsetX = { -1000 }, animationSpec = tween(300)
                    ) + fadeIn(),
                    exit = slideOutHorizontally(
                        targetOffsetX = { 1000 }, animationSpec = tween(300)
                    ) + fadeOut(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ellipse),
                        contentDescription = null,
                        Modifier
                            .size(450.dp)
                            .rotate(size)
                    )
                }
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter = fadeIn(
                        // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                        animationSpec = tween(300)
                    )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.auth_start_img),
                        contentDescription = null,
                        Modifier
                            .size(size.dp)
                            .padding(top = 35.dp)
                    )

                }


            }

        }
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                AuthText(text = "Lets's\nget Started", color = Color.White )
                AuthText(text = "Everything start from here", sizeWithSp = 12, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(30.dp))
            RoundedBtn(onClick = {
                navigator.push(LoginScreen())
            }, text = "Login", textSizeSp = 25 , textColor = blue2)
            RoundedBtn(onClick = {
                navigator.push(SignupScreen())
            }, text = "Signup", textSizeSp = 25 , textColor = blue2)
        }
    }
}


