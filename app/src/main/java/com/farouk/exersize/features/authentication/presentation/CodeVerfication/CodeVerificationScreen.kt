 package com.farouk.exersize.features.authentication.presentation.CodeVerfication

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.farouk.exersize.features.authentication.presentation.AuthViewModel
import com.farouk.exersize.features.authentication.presentation.components.LoadingDialog
import com.farouk.exersize.features.authentication.presentation.login.components.ShowVerifyCodeErrorDialog
import com.farouk.exersize.features.authentication.presentation.login.components.VerifyCodeUI
import com.farouk.exersize.theme.blue1


data class CodeVerificationScreen(val phone: String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Log.d("phone  is ", phone)
        val viewModel: AuthViewModel = getViewModel()
        OTPLogin(viewModel, phone , navigator)
    }


}


@Composable
fun OTPLogin(
    viewModel: AuthViewModel, phone: String , navigator : Navigator
) {

    val context = LocalContext.current
    val vibrator: Vibrator? = ContextCompat.getSystemService(context, Vibrator::class.java)
    val OTPColor = remember { mutableStateOf(blue1) }
    val errorDialog = remember { mutableStateOf(true) }
    var (pinValue, onPinValueChange) = remember { mutableStateOf("") }


    val state = viewModel.codeVerifyState.value
    val reqIsSent = remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()

    when {
        state.isLoading -> {
            LoadingDialog(onDismissRequest = {})
        }


        state.data?.status == true && reqIsSent.value -> {
          /*  if (state.data.msg.gender == null)
                navigator.push(StepperScreen())
            else */
            viewModel.saveUserId(state.data.msg.id.toString())
            viewModel.saveChatId(state.data.msg.chat_id.toString())
            viewModel.navigateToHome(navigator)
            reqIsSent.value = false

            // navigator.push(StepperScreen())
            OTPColor.value = Color.Green

        }

        state.data?.status == false && pinValue.length == 4 && reqIsSent.value -> {
            vibrateApp(vibrator)
            vibrateApp(vibrator)
            OTPColor.value = Color.Red
            pinValue = ""
            onPinValueChange("")
            reqIsSent.value = false

        }

        state.data?.error_msg?.isNotEmpty() == true -> {
            ShowVerifyCodeErrorDialog(state = state, errorDialog = errorDialog)
        }
    }

    VerifyCodeUI(
        OTPColor = OTPColor,
        pinValue = pinValue,
        onPinValueChange = onPinValueChange,
        errorDialog = errorDialog ,
        viewModel = viewModel,
        phone = phone,
        reqIsSent = reqIsSent
    )






}

fun vibrateApp(vibrator: Vibrator?) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator?.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    } else {
        //deprecated in API 26
        vibrator?.vibrate(300);
    }
}

