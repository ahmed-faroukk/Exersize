package com.mala.grad_project.Screenns.CoachScreen.Conmposble

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.darkYellow


@Composable
fun CardOfPortfolio(
    beforeImge: Painter,
    afterImage: Painter,
    comment:String
){

    Column(
        modifier = Modifier
            .size(width = 300.dp, height = 280.dp)
            .padding(10.dp)
            .shadow(elevation = 20.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),

            ) {

            Row(
                Modifier
                    .wrapContentSize()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically

                ) {
                Column (
                    horizontalAlignment=Alignment.CenterHorizontally,
                ){

                    Text(
                        text = "Before",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = darkYellow
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = beforeImge,
                        contentDescription = "error image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(50.dp))


                    )
                }


                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .height(5.dp)
                        .background(color = blue1)
                )

                Column (
                    horizontalAlignment=Alignment.CenterHorizontally
                ){
                    Text(
                        text = "After",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color =  darkYellow
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Image(
                        painter = afterImage,
                        contentDescription = "error image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )
                }

            }



        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    blue1,
                    RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
        ){
            Text(
                text = comment ,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)

            )
        }
    }
}



