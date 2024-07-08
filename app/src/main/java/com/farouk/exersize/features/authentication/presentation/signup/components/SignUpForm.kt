package com.farouk.exersize.features.authentication.presentation.signup.components

import android.os.Vibrator
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
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
import com.farouk.exersize.features.authentication.domain.entity.signup.UserSignupModel
import com.farouk.exersize.features.authentication.presentation.AuthViewModel
import com.farouk.exersize.features.authentication.presentation.CodeVerfication.vibrateApp
import com.farouk.exersize.features.authentication.presentation.components.AuthLoginText
import com.farouk.exersize.features.authentication.presentation.components.AuthText
import com.farouk.exersize.features.authentication.presentation.components.OutLineTextFieldNumber
import com.farouk.exersize.features.authentication.presentation.components.OutLineTextFieldString
import com.farouk.exersize.features.authentication.presentation.components.RoundedBtn
import com.farouk.exersize.features.authentication.presentation.components.ShowSnackBar
import com.farouk.exersize.features.authentication.presentation.login.LoginScreen
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.blue3
import com.farouk.exersize.theme.darkYellow
import com.farouk.exersize.utils.validation.InputWrapper
import com.farouk.exersize.utils.validation.ValidationType


@Composable
fun SignUpForm(
    viewModel: AuthViewModel,
    navigator: Navigator,
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    phone: MutableState<String>,
    infoDialog: MutableState<Boolean>,
    errorDialog: MutableState<Boolean>
) {
    val context = LocalContext.current
    val vibrator: Vibrator? = ContextCompat.getSystemService(context, Vibrator::class.java)
    val textInputWrapper =  InputWrapper(validationType = ValidationType.Text)
    val text2InputWrapper = InputWrapper(validationType = ValidationType.Text)
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
                    text = stringResource(id = R.string.create_an_account),
                    sizeWithSp = 24,
                )
                AuthText(
                    text = stringResource(id = R.string.enter_your_details_to_continue),
                    sizeWithSp = 16,
                    color = blue3,
                    fontWeight = FontWeight.Thin
                )
                Spacer(modifier = Modifier.height(40.dp))
                AuthText(text = stringResource(R.string.first_name), sizeWithSp = 16)
                OutLineTextFieldString(
                    onNameChange = { text ->
                        // Update the state in InputWrapper when the text changes
                        firstName.value = text
                    },
                    label = stringResource(R.string.enter_your_first_name),
                    isEmptyRequest = isEmptyReq
                )
                Spacer(modifier = Modifier.height(10.dp))

                AuthText(text = stringResource(R.string.last_name), sizeWithSp = 16)
                OutLineTextFieldString(
                    onNameChange = {
                        lastName.value = it },
                    label = stringResource(R.string.enter_your_last_name),
                    isEmptyRequest = isEmptyReq
                )
                Spacer(modifier = Modifier.height(10.dp))

                AuthText(text = stringResource(id = R.string.mobile_number), sizeWithSp = 16)
                OutLineTextFieldNumber(
                    onNameChange = {
                        phone.value = it
                    },
                    label = stringResource(id = R.string.enter_your_phone_number),
                    inputWrapper = phoneInputWrapper,
                    isEmptyRequest = isEmptyReq

                )
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
                RoundedBtn(
                    onClick = {
                        val data: UserSignupModel = UserSignupModel(
                            phone = phone.value,
                            fname = firstName.value,
                            lname = lastName.value
                        )
                        infoDialog.value = true
                        errorDialog.value = true
                        if (phoneInputWrapper.safeRequest(phone.value)&& textInputWrapper.safeRequest(firstName.value)&& text2InputWrapper.safeRequest(lastName.value)) {
                            viewModel.createNewAccount(data)
                        }
                        else {
                            vibrateApp(vibrator)
                            isEmptyReq.value = true
                        }



                    },
                    text = "Create an account"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    AuthText(
                        text = stringResource(id = R.string.already_a_member),
                        sizeWithSp = 14,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.surface
                    )
                    AuthLoginText(
                        text = stringResource(R.string.login),
                        sizeWithSp = 14,
                        color = darkYellow,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.clickable {
                            navigator.replace(LoginScreen())
                        })
                }
            }


        }
    }
}