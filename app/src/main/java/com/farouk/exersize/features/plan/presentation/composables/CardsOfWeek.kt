package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.theme.blue2
import com.farouk.exersize.theme.darkYellow


@Composable
fun WeekDayCard(
    dayOfWeek: String, fullName: String,
    isSelected: Boolean,
    onClick: (String) -> Unit,
) {

    Card(
        modifier = androidx.compose.ui.Modifier
            .size(120.dp)
            .padding(8.dp)
            .clickable {
                onClick(dayOfWeek)
            }   // Use clickable modifier here
            .shadow(elevation = 25.dp),
        colors = CardDefaults.cardColors(
            blue2
        ),
        shape = RoundedCornerShape(10.dp),
        border = if (isSelected) BorderStroke(3.dp, darkYellow) else null,

        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${dayOfWeek[0].uppercase()}${dayOfWeek[1].uppercase()}${dayOfWeek[2].uppercase()}",
                modifier = Modifier.padding(top = 5.dp),
                fontSize = 17.sp,
                color = Color.White,
                )
            Text(
                text = fullName,
                fontSize = 13.sp,
                color = darkYellow,
                modifier = Modifier.padding(bottom = 1.dp)
            )
        }

    }
}






