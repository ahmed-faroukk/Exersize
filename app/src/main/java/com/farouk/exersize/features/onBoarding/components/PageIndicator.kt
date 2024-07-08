package com.CyberDunkers.Sla7ly.presentation.onBoarding.components

import android.provider.CalendarContract.Colors
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.farouk.exersize.theme.brightYellow
import com.farouk.exersize.theme.darkYellow
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json.Default.configuration

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = darkYellow,
    unSelectedColor : Color = Color.White,
    isOpened : Boolean = false
) {

    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        repeat(pageSize) { page ->
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.screenWidthDp.dp
            val boxWidth = (screenWidth / 3.2f )  ;
            val isPageSelected = page <= selectedPage

            val width = animateDpAsState(
                targetValue = boxWidth.value.dp,
                tween(
                    durationMillis = 400,
                    easing = LinearOutSlowInEasing
                ),
                label = ""
            )


            Box(
                modifier = Modifier
                    .width(width.value)
                    .height(5.dp)
                    .clip(CircleShape)
                    .background(color = if (isPageSelected) selectedColor else unSelectedColor)
            )
            Spacer(modifier = Modifier.width(2.dp))

        }

    }

}