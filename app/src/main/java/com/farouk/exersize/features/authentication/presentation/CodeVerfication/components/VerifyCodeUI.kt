package com.farouk.exersize.features.authentication.presentation.login.components

import android.os.Vibrator
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.common.Constants
import com.farouk.exersize.features.authentication.data.Model.codeVerfication.VerifyCodeModel
import com.farouk.exersize.features.authentication.presentation.AuthViewModel
import com.farouk.exersize.features.authentication.presentation.components.AuthText
import com.farouk.exersize.features.authentication.presentation.components.PinView
import com.farouk.exersize.features.authentication.presentation.components.RoundedBtn
import kotlinx.coroutines.delay

@Composable
fun VerifyCodeUI(
    OTPColor: MutableState<Color>,
    pinValue: String,
    onPinValueChange: (String) -> Unit,
    errorDialog: MutableState<Boolean>,
    viewModel: AuthViewModel,
    phone: String,
    reqIsSent : MutableState<Boolean>
) {
    val vibrator = LocalContext.current.getSystemService(Vibrator::class.java)
    val isEmptyReq = remember { mutableStateOf(false) }
    val isShaking by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(false) }

    val translationXState by animateFloatAsState(
        targetValue = if (isShaking) 15f else 0f,
        animationSpec = InfiniteRepeatableSpec(
            animation = tween(300, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(isVisible) {
        if (!isVisible) {
            delay(1)
            isVisible = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            visible = isVisible,
            enter = scaleIn(tween(700)) + fadeIn(tween(1000))
        ) {
            VerifyCodeContent(
                pinValue = pinValue,
                onPinValueChange = onPinValueChange,
                isShaking = isShaking,
                translationXState = translationXState,
                OTPColor = OTPColor,
                viewModel = viewModel,
                phone = phone,
                errorDialog = errorDialog,
                reqIsSent =reqIsSent
            )
        }
    }
}

@Composable
private fun VerifyCodeContent(
    pinValue: String,
    onPinValueChange: (String) -> Unit,
    isShaking: Boolean,
    translationXState: Float,
    OTPColor: MutableState<Color>,
    viewModel: AuthViewModel,
    phone: String,
    errorDialog: MutableState<Boolean>,
    reqIsSent : MutableState<Boolean>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 100.dp, bottomEnd = 100.dp)
            )
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        AuthText(
            text = stringResource(R.string.verfied_your_number),
            sizeWithSp = 20,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.surface,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(10.dp))

        AuthText(
            text = stringResource(R.string.we_sent_you_a_code_please_enter_it),
            sizeWithSp = 15,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.surface,
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = if (isShaking) translationXState.dp else 0.dp),
            contentAlignment = Alignment.Center
        ) {
            PinView(
                pinText = pinValue,
                unfoucsUnderLineColor = OTPColor.value,
                onPinTextChange = onPinValueChange,
                type = Constants.PIN_VIEW_TYPE_BORDER,
                containerSize = 35.dp
            )
        }

        Spacer(modifier = Modifier.height(150.dp))

        RoundedBtn(
            onClick = {
                if (pinValue.length < 4){
                    OTPColor.value = Color.Red
                    onPinValueChange("")
                }else{
                    println(phone)
                    println(pinValue)
                    viewModel.verifyCode(VerifyCodeModel(phone, pinValue))
                    errorDialog.value = true
                    reqIsSent.value = true
                }

            },
            text = stringResource(R.string.continue_btn)
        )
    }
}
