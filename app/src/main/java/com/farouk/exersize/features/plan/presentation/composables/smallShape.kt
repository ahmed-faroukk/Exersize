package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SmallShape(
    date:String
){
    Box(
        modifier = Modifier
            .size(width = 40.dp, height = 10.dp)
            .shadow(elevation = 3.dp)
            .background(androidx.compose.material3.MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(50.dp))
    ){
        Text(
            modifier = Modifier
                .size(width = 39.dp, height = 10.dp)
                ,
            text =date,
            fontSize =7.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF003566)
        )
    }

}


