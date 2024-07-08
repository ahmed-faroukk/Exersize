package com.farouk.exersize.features.authentication.presentation.signup.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.farouk.exersize.features.authentication.presentation.components.AuthInfoDialog
import com.farouk.exersize.features.authentication.presentation.components.ErrorDialog
import com.farouk.exersize.features.authentication.presentation.signup.SignUpState
import kotlinx.coroutines.launch

@Composable
fun ShowErrorDialog(state: SignUpState, errorDialog: MutableState<Boolean>) {
    if (errorDialog.value) {
        ErrorDialog(onDismissRequest = {
            errorDialog.value = false
        }, title = "Server Error", desc = state.error)
    }
}

@Composable
fun ShowInfoDialog(msg: String, infoDialog: MutableState<Boolean>) {
    if (infoDialog.value) {
        AuthInfoDialog(
            title = "Login Problem",
            desc = msg
        ) {
            infoDialog.value = false
        }
    }
}

