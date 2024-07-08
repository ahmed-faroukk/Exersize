package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.blue2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Payment(painter: Painter, type:String, isSelected:Boolean, onSelected:()->Unit){
    var backColor by remember { mutableStateOf(Color.White) }
    var textColor by remember { mutableStateOf(blue1) }

    Card(
        modifier = Modifier.size(height =200.dp, width = 180.dp),
        colors = CardDefaults.cardColors(
            backColor
        ),
        elevation = CardDefaults.cardElevation(
            15.dp
        ), onClick = {
            onSelected()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = "error image",
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = type,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
    LaunchedEffect(isSelected){
        if(isSelected){
            backColor= blue2
            textColor= Color.White
        }
        else{
            backColor= Color.White
            textColor= blue2
        }
    }
}
