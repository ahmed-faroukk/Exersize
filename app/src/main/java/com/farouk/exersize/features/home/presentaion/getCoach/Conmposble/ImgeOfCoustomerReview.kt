package com.mala.grad_project.Screenns.CoachScreen.Conmposble

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.farouk.exersize.R

@Composable
fun CircleCoachImage(
    painter: Painter,
    size:Int
) {

    Box (
        contentAlignment = Alignment.TopStart,

    ){
        val borderWidth = 1.dp

        Image(
            painter = painter,
            contentDescription = "error image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size.dp)
                .padding(borderWidth)
                .clip(RoundedCornerShape(120.dp))




        )
    }
}
@Composable
@Preview
fun test(){
    CircleCoachImage(painterResource(id = R.drawable.man), size = 20)
}