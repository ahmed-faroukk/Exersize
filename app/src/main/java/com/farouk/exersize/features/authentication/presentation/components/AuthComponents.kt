package com.farouk.exersize.features.authentication.presentation.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.common.Constants.PIN_VIEW_TYPE_BORDER
import com.farouk.exersize.features.authentication.common.Constants.PIN_VIEW_TYPE_UNDERLINE
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.blue2
import com.farouk.exersize.theme.blue3
import com.farouk.exersize.theme.darkYellow
import com.farouk.exersize.utils.validation.InputWrapper
import com.farouk.exersize.utils.validation.Validation.isValidPhone
import com.farouk.exersize.utils.validation.Validation.validateText
import com.farouk.exersize.utils.validation.ValidationMessageType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RoundedBtn(
    onClick: () -> Unit,
    text: String,
    textColor: Color = blue3,
    buttonColor: Color = darkYellow,
    modifier: Modifier = Modifier,
    textSizeSp: Int = 15,
) {

    Button(
        onClick = {
            onClick()
        }, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
            buttonColor
        ), modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp, top = 10.dp)
    ) {
        AuthText(
            text = text,
            color = textColor,
            fontWeight = FontWeight.ExtraBold,
            sizeWithSp = textSizeSp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AuthText(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Medium,
    sizeWithSp: Int = 20,
    color: Color = colorScheme.surface,
    textAlign: TextAlign = TextAlign.Start,

    ) {

    Text(
        text = text,
        color = color,
        modifier = modifier.padding(start = 20.dp),
        fontSize = sizeWithSp.sp,
        fontWeight = fontWeight,
        fontFamily = FontFamily(Font(R.font.roboto_black)),
        //fontFamily = FontFamily(Font(R.font.elmessiri)),
        textAlign = textAlign
    )

}

@Composable
fun AuthLoginText(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Medium,
    sizeWithSp: Int = 20,
    color: Color = colorScheme.surface,
    textAlign: TextAlign = TextAlign.Start,

    ) {

    Text(
        text = text,
        color = color,
        modifier = modifier,
        fontSize = sizeWithSp.sp,
        fontWeight = fontWeight,
        fontFamily = FontFamily(Font(R.font.roboto_black)),
        //fontFamily = FontFamily(Font(R.font.elmessiri)),
        textAlign = textAlign
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutLineTextFieldString(
    onNameChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    value: MutableState<String> = remember {
        mutableStateOf("")
    },
    isEmptyRequest : MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
) {
    val isValid = remember {
        mutableStateOf(false)
    }
    val inputWrapper = InputWrapper(isValid = isValid)
    var isFirstValueChange by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(TextFieldValue(value.value)) }
    var labelColor by remember {
        mutableStateOf(blue1)
    }
    if (isEmptyRequest.value && text.text.validateText() != ValidationMessageType.Valid)
        labelColor = Color.Red
    OutlinedTextField(
        value = text,
        label = { Text(text = label, color = colorScheme.surface) },
        onValueChange = {
            isEmptyRequest.value = false
            text = it
            inputWrapper.onValueChange(it.text)
            onNameChange(it.text)
            labelColor = if (inputWrapper.safeRequest(text.text)) blue1 else Color.Red

            Log.d("is valid ", inputWrapper.isValid.value.toString())

        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = labelColor,
            selectionColors = TextSelectionColors(
                handleColor = darkYellow,
                backgroundColor = blue1
            ),
            unfocusedIndicatorColor = labelColor,
            containerColor = Color.Transparent,
            cursorColor = colorScheme.surface,
            focusedTextColor = colorScheme.surface,
            unfocusedTextColor = colorScheme.surface
        ),
        modifier = modifier, singleLine = true,
        keyboardOptions = keyboardOptions,

        )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutLineTextFieldNumber(
    inputWrapper: InputWrapper,
    onNameChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp),
    isEmptyRequest : MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    ) {
    var isFirstValueChange by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var labelColor by remember {
        mutableStateOf(blue1)
    }
    if (isEmptyRequest.value && text.text.isValidPhone() != ValidationMessageType.Valid)
        labelColor = Color.Red
    Box {

        OutlinedTextField(
            value = text,
            label = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AuthLoginText(text = "+20  ", sizeWithSp = 15)
                    Text(text = label, color = colorScheme.surface)
                }
            },
            onValueChange = {
                isEmptyRequest.value = false
                text = it
                inputWrapper.onValueChange(it.text)
                onNameChange(it.text)
                labelColor = if (inputWrapper.safeRequest(text.text)) blue1 else Color.Red

            }, colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = labelColor,
                unfocusedIndicatorColor = labelColor,
                containerColor = Color.Transparent,
                cursorColor = colorScheme.surface,
                focusedTextColor = colorScheme.surface,
                unfocusedTextColor = colorScheme.surface,
                selectionColors = TextSelectionColors(
                    handleColor = darkYellow,
                    backgroundColor = blue1
                ),


                ), modifier = modifier, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError
        )
    }
    LaunchedEffect(text.text) {
        isFirstValueChange = true
    }


}

@Composable
fun PinView(
    pinText: String,
    onPinTextChange: (String) -> Unit,
    digitColor: Color = colorScheme.surface,
    digitSize: TextUnit = 20.sp,
    containerSize: Dp = digitSize.value.dp * 2,
    digitCount: Int = 4,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
    unfoucsUnderLineColor: Color = colorScheme.surface,
    foucsUnderLineColor: Color = blue1,


    ) {
    BasicTextField(value = pinText,
        onValueChange = onPinTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(digitCount) { index ->
                    DigitView(
                        index,
                        pinText,
                        digitColor,
                        digitSize,
                        containerSize,
                        type = type,
                        unfoucsUnderLineColor = unfoucsUnderLineColor,
                        foucsUnderLineColor = foucsUnderLineColor
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        })
}


@Composable
private fun DigitView(
    index: Int,
    pinText: String,
    digitColor: Color,
    digitSize: TextUnit,
    containerSize: Dp,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
    unfoucsUnderLineColor: Color = colorScheme.surface,
    foucsUnderLineColor: Color = blue1,

    ) {
    val modifier = if (type == PIN_VIEW_TYPE_BORDER) {
        Modifier
            .size(containerSize)
            .border(
                width = 1.dp,
                color = digitColor,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(bottom = 3.dp)
    } else Modifier.width(containerSize)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AuthLoginText(
            text = if (index >= pinText.length) "" else pinText[index].toString(),
            color = digitColor,
            modifier = modifier,
            textAlign = TextAlign.Center,
            sizeWithSp = 25
        )
        if (type == PIN_VIEW_TYPE_UNDERLINE) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(if (index >= pinText.length) unfoucsUnderLineColor else foucsUnderLineColor)
                    .height(2.dp)
                    .width(containerSize)
            )
        }
    }
}


@Composable
fun LoadingDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // onDismissRequest =  LoadingDialog.value = false
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(
                        colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AFLoading(
                    color1 = colorScheme.primary,
                    color2 = colorScheme.primary,
                    color3 = colorScheme.primary
                )
            }
        }
    }
}


@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    title: String,
    desc: String
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        blue3,
                        shape = RoundedCornerShape(16.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                    AuthText(
                        text = title,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp), sizeWithSp = 15
                    )
                    AuthText(
                        text = desc,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp) , sizeWithSp = 13,
                    )


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss" , color = darkYellow)
                    }
                }
            }
        }
    }
}


@Composable
fun AFLoading(
    // Modifier for customizing the layout of the loading animation
    modifier: Modifier = Modifier,

    // Size of each circle in the loading animation
    circleSize: Dp = 20.dp,

    // Space between the circles in the loading animation
    spaceBetween: Dp = 10.dp,

    // Vertical travel distance of each circle during animation
    travelDistance: Dp = 20.dp,

    // Duration of the animation cycle for each circle
    animationDuration: Int = 1200,

    // Delay between the start of animations for consecutive circles
    delayBetweenCircles: Int = 100,

    // Colors for the circles
    color1: Color = Color.Yellow,
    color2: Color = Color.Cyan,
    color3: Color = Color.White,

    // Repeat mode for the animation (e.g., Restart, Reverse)
    repeatMode: RepeatMode = RepeatMode.Restart,

    // Easing function for the animation
    easing: Easing = LinearOutSlowInEasing
) {
    val circles = List(3) { index ->
        remember { Animatable(initialValue = 0f) }
            .apply {
                LaunchedEffect(key1 = this) {
                    delay(index * delayBetweenCircles.toLong())
                    animateTo(
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = keyframes {
                                durationMillis = animationDuration
                                0.0f at 0 with easing
                                1.0f at (animationDuration / 4) with easing
                                0.0f at (animationDuration / 2) with easing
                                0.0f at animationDuration with easing
                            },
                            repeatMode = repeatMode
                        )
                    )
                }
            }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        circleValues.forEachIndexed() { index, value ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = when (index) {
                            0 -> color1
                            1 -> color2
                            else -> color3
                        },
                        shape = CircleShape
                    )
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthSnackBar(msg: String) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(content = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                coroutineScope.launch {

                    val snackBarResult = snackBarHostState.showSnackbar(
                        message = "Snackbar is here",
                        actionLabel = "Ok",
                        duration = SnackbarDuration.Short
                    )
                    when (snackBarResult) {
                        SnackbarResult.ActionPerformed -> {
                            Log.d("Snackbar", "Action Performed")
                        }

                        else -> {
                            Log.d("Snackbar", "Snackbar dismissed")
                        }
                    }
                }

            }) {
                Text(text = msg, color = Color.White)
            }
        }
    }, snackbarHost = { SnackbarHost(hostState = snackBarHostState) })
}

@JvmOverloads
@Composable
fun AuthInfoDialog(
    title: String = "Message",
    desc: String = "Your Message",
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {


            Box(
                modifier = Modifier
                    .background(
                        color = blue2,
                        shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                    )
                    .align(Alignment.Center),
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    //.........................Text: title
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = darkYellow,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = darkYellow,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button
                    com.farouk.exersize.base.composables.RoundedBtn(
                        onClick = { onDismiss() },
                        text = "ok",
                        textColor = colorScheme.surface,
                        buttonColor = darkYellow
                    )


                    //.........................Spacer
                    Spacer(modifier = Modifier.height(30.dp))

                }


            }

        }
    }
}

@Composable
fun ShowSnackBar(msg: String) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("msg") },
                icon = { Icon(Icons.Filled.Info, contentDescription = "") },
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("OK")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) { /* ... */ }
    }
}