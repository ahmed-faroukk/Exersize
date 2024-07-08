package com.farouk.exersize.features.authentication.presentation.signup

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
import com.farouk.exersize.features.authentication.presentation.signup.components.ShowErrorDialog
import com.farouk.exersize.features.authentication.presentation.signup.components.ShowInfoDialog
import com.farouk.exersize.features.authentication.presentation.signup.components.SignUpForm

class SignupScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: AuthViewModel = getViewModel()
        Signup(viewModel)
    }

}



@Composable
fun Signup(viewModel: AuthViewModel) {
    val state = viewModel.signUpState.value

    val errorDialog = remember { mutableStateOf(true) }
    val navigator = LocalNavigator.currentOrThrow

    val firstName = remember {
        mutableStateOf("")
    }
    val lastName = remember {
        mutableStateOf("")
    }
    val phone = remember {
        mutableStateOf("")
    }

    val infoDialog = remember { mutableStateOf(false) }



        when {

            state.data?.status == true -> {
                navigator.push(CodeVerificationScreen(phone.value))
            }

            state.isLoading -> {
                LoadingDialog(onDismissRequest = {})
                errorDialog.value = true
            }

            state.error.isNotEmpty() -> {
                ShowErrorDialog(state, errorDialog)
            }

            state.data?.error_msg?.isNotEmpty() == true -> {
                ShowInfoDialog(state.data.error_msg, infoDialog)
            }
        }


    SignUpForm(viewModel, navigator, firstName, lastName, phone, infoDialog, errorDialog)


}
