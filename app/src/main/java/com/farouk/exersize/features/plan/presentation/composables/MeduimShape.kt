package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MeduimShape(
    date:String
){
    Card(
        modifier = Modifier
            .size(width = 65.dp, height = 15.dp)
            .shadow(elevation = 10.dp)
            .background(MaterialTheme.colorScheme.onPrimary, RoundedCornerShape(50.dp))
            .padding(start = 2.dp)
        ,
        colors = CardDefaults.cardColors(
            Color.White
        )
    ){
        Text(
            modifier = Modifier
                .size(width = 60.dp, height = 19.dp)
                .padding(top = 0.4.dp)
            ,
            text =date,
            fontSize =11.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF003566)
        )
    }

}