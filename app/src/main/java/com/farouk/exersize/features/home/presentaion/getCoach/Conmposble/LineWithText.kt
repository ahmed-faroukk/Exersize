package com.mala.grad_project.Screenns.CoachScreen.Conmposble

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun LineTextViewChoachScreen(
    text:String
){
    Row (
        modifier = Modifier.fillMaxWidth(),
        Arrangement.SpaceBetween
    ){
        singleHorizontalLine(120)
        Text(
            text =text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color= MaterialTheme.colorScheme.surface,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        singleHorizontalLine(120)
    }
}
@Preview
@Composable
fun show(){
LineTextViewChoachScreen("hello")
}