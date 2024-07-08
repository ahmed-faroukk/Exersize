package com.farouk.exersize.features.authentication.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.farouk.exersize.features.authentication.presentation.AuthViewModel
import com.farouk.exersize.features.authentication.presentation.CodeVerfication.CodeVerificationScreen
import com.farouk.exersize.features.authentication.presentation.components.LoadingDialog
import com.farouk.exersize.features.authentication.presentation.login.components.LoginForm
import com.farouk.exersize.features.authentication.presentation.login.components.ShowLoginErrorDialog
import com.farouk.exersize.features.authentication.presentation.login.components.ShowLoginInfoDialog

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: AuthViewModel = getViewModel()
        Login(viewModel)
    }

    @Composable
    fun Login(viewModel: AuthViewModel) {
        val navigator = LocalNavigator.currentOrThrow
        val errorDialog = remember { mutableStateOf(true) }

        val phone = remember {
            mutableStateOf("")
        }
        val state = viewModel.loginState.value

        val infoDialog = remember { mutableStateOf(false) }


        when {
            state.isLoading -> {
                LoadingDialog(onDismissRequest = {})
            }
            state.error.isNotEmpty() -> {
                ShowLoginErrorDialog(state, errorDialog)
            }
            state.data?.status == true -> {
                navigator.push(CodeVerificationScreen(phone.value))
            }
            state.data?.status == false ->{
                ShowLoginInfoDialog(infoDialog = infoDialog )
            }
            else -> {}
        }

        LoginForm(viewModel = viewModel, navigator = navigator, phone = phone , infoDialog = infoDialog ,errorDialog = errorDialog)

    }
}
