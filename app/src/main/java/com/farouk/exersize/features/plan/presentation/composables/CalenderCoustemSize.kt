package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.theme.darkYellow

@Composable
fun CalenderCoustemSize(
    typeOfText:String?=null,
    date:String,
    calenderImage: Painter,
    fontsize:Int,
    iconSize:Int
){

    Row (
        Modifier
            .wrapContentSize()
            .padding(2.dp),

        verticalAlignment = Alignment.CenterVertically
    ){
        Box(modifier = Modifier.size(iconSize.dp)) {

            Image(
                painter = calenderImage, contentDescription = null,
                androidx.compose.ui.Modifier.size(iconSize.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(2.dp)
        ) {
            typeOfText?.let {
                Text(
                    text = it,
                    fontSize = fontsize.sp,
                    color = darkYellow,
                    modifier =  Modifier.padding(start =2.dp)
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            MeduimShape(date)
        }
    }
}