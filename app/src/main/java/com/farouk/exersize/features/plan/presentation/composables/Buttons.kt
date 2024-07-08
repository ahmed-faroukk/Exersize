package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.theme.blue1

@Composable
fun BlueButton(
    onClick: () -> Unit, modifier: Modifier = Modifier,
    text: String, color: Color = blue1
) {
    Button(
        onClick = onClick,
        modifier = modifier, shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            color
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp, textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}


@Composable
fun outlineButton(
    onClick: () -> Unit, modifier: Modifier = Modifier,
    text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            Color.White
        ),
        border = BorderStroke(1.dp, blue1),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp, textAlign = TextAlign.Center,
            color = blue1
        )
    }
}