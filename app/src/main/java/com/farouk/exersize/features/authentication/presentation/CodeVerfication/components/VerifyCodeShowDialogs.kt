package com.farouk.exersize.features.authentication.presentation.login.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.presentation.CodeVerfication.VerifyCodeState
import com.farouk.exersize.features.authentication.presentation.components.AuthInfoDialog
import com.farouk.exersize.features.authentication.presentation.components.ErrorDialog

@Composable
fun ShowVerifyCodeErrorDialog(state: VerifyCodeState, errorDialog: MutableState<Boolean>) {
    if (errorDialog.value) {
        ErrorDialog(onDismissRequest = {
            errorDialog.value = false
        }, title = "Server Error", desc = state.error)
    }
}

@Composable
fun ShowVerifyCodeInfoDialog( infoDialog: MutableState<Boolean>) {
    if (infoDialog.value)
        AuthInfoDialog(
            title = stringResource(R.string.this_number_doesn_t_have_an_account),
            desc = stringResource(R.string.please_make_an_account_and_try_again)
        ) {
            infoDialog.value = false
        }
}

