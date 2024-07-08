package com.mala.grad_project.Screenns.planCard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.singleHorizontalLine


@Composable
fun LineTextViewPlanScreen(
    text:String
){
    Row (
        modifier = Modifier.fillMaxWidth(),
        Arrangement.SpaceBetween
    ){
        singleHorizontalLine(80)
        Text(
            text =text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color= MaterialTheme.colorScheme.surface,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        singleHorizontalLine(80)
    }
}