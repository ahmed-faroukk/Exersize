package com.farouk.exersize.base.animations

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun AFAnimateAsFloat(
    modifier: Modifier = Modifier,
    isVisible : MutableState<Boolean> =  rememberSaveable { mutableStateOf(false) },
    content: @Composable (Modifier , MutableState<Boolean>) -> Unit = { size , isVisable ->

},
    initValue: Float = 400f,
    targetValue: Float = 250f,
    animationSpec: AnimationSpec<Float> = tween(200),
    delay: Long = 500,
    onSizeChange: @Composable (Float , MutableState<Boolean>) -> Unit = { size , isVisable ->

    }, // Callback function for size change
) {

    val size by animateFloatAsState(
        targetValue = if (!isVisible.value) initValue else targetValue,
        label = "", animationSpec = animationSpec
    )

    LaunchedEffect(isVisible.value) {
        // Hide the box for 200 milliseconds
        if (!isVisible.value) {
            delay(delay)
            isVisible.value = true
        }
    }

    content(modifier.size(size.dp) , isVisible)
    onSizeChange(size , isVisible)

}