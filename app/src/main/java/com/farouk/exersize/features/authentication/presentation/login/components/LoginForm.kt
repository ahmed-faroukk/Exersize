package com.farouk.exersize.features.authentication.presentation.login.components

import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import cafe.adriel.voyager.navigator.Navigator
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginModel
import com.farouk.exersize.features.authentication.presentation.AuthViewModel
import com.farouk.exersize.features.authentication.presentation.CodeVerfication.vibrateApp
import com.farouk.exersize.features.authentication.presentation.components.AuthLoginText
import com.farouk.exersize.features.authentication.presentation.components.AuthText
import com.farouk.exersize.features.authentication.presentation.components.OutLineTextFieldNumber
import com.farouk.exersize.features.authentication.presentation.components.RoundedBtn
import com.farouk.exersize.features.authentication.presentation.signup.SignupScreen
import com.farouk.exersize.theme.blue3
import com.farouk.exersize.theme.darkYellow
import com.farouk.exersize.utils.validation.InputWrapper
import com.farouk.exersize.utils.validation.ValidationType

@Composable
fun LoginForm(
    viewModel: AuthViewModel,
    navigator: Navigator,
    phone: MutableState<String>,
    infoDialog: MutableState<Boolean>,
    errorDialog: MutableState<Boolean>,
){
    val context = LocalContext.current
    val vibrator: Vibrator? = ContextCompat.getSystemService(context, Vibrator::class.java)
    val phoneInputWrapper = InputWrapper(validationType = ValidationType.Phone)
    val isEmptyReq = remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(Modifier.fillMaxWidth()) {
            item {
                AuthText(
                    text = stringResource(R.string.welcome_back),
                    sizeWithSp = 24,
                )
                AuthText(
                    text = stringResource(R.string.enter_your_mobile_number),
                    sizeWithSp = 16,
                    color = blue3,
                    fontWeight = FontWeight.Thin
                )
                Spacer(modifier = Modifier.height(40.dp))
                Spacer(modifier = Modifier.height(10.dp))

                AuthText(text = stringResource(R.string.mobile_number), sizeWithSp = 16)
                OutLineTextFieldNumber(
                    onNameChange = { phone.value = it },
                    label = stringResource(R.string.enter_your_phone_number),
                    inputWrapper = phoneInputWrapper,
                    isEmptyRequest = isEmptyReq

                )
                Spacer(modifier = Modifier.height(50.dp))

            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                RoundedBtn(
                    onClick = {
                        infoDialog.value = true
                        errorDialog.value = true
                        if (phoneInputWrapper.safeRequest(phone.value)) {
                            viewModel.login(UserLoginModel(phone = phone.value))
                        }
                        else {
                            vibrateApp(vibrator)
                            isEmptyReq.value = true
                        }

                    },
                    text = stringResource(id = R.string.login)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    AuthText(
                        text = stringResource(R.string.don_t_have_account),
                        sizeWithSp = 14,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.surface
                    )
                    AuthLoginText(
                        text = stringResource(R.string.signup),
                        sizeWithSp = 14,
                        color = darkYellow,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            navigator.replace(SignupScreen())
                        })
                }
            }


        }
    }
}