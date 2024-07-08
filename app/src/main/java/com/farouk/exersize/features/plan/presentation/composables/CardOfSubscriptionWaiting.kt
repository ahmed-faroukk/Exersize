package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.R
import com.farouk.exersize.features.plan.domain.Entitiy.GetPlanResponse
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.brightYellow
import com.farouk.exersize.theme.darkYellow

@Composable
fun CardOfSub(
    getPlansResponse: GetPlanResponse
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(blue1),
        Arrangement.Center

    ){


        Box(
            Modifier
                .padding(10.dp)
                .padding(bottom = 50.dp)
                .shadow(elevation = 30.dp)

        ) {


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(5.dp)),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    Arrangement.SpaceBetween
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(50.dp))
                        Row {
                            Text(
                                text = "Captain: ",

                                fontWeight = FontWeight.Bold,
                                color = blue1,
                                fontSize = 20.sp
                            )
                            Text(
                                text = "${getPlansResponse.coach.fname} ${getPlansResponse.coach.lname}",
                                fontWeight = FontWeight.Bold,
                                color = blue1,
                                fontSize = 20.sp
                            )
                        }
                        Row {
                            Text(

                                text = "Package: ",
                                modifier = Modifier.padding(top = 5.dp),
                                fontSize = 15.sp,
                                color = darkYellow,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${getPlansResponse.paackage.number_of_months} Months",
                                modifier = Modifier.padding(top = 5.dp),
                                fontSize = 15.sp,
                                color = blue1,
                                fontWeight = FontWeight.Bold

                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.height(50.dp))
                             Image(
                                    painter = painterResource(id = R.drawable.expired),
                                    contentDescription = null,
                                    Modifier
                                        .size(22.dp)
                                        .padding(top = 2.dp)

                                )
                                Text(
                                    text = "Waiting",
                                    fontSize = 19.sp,
                                    color = brightYellow

                                )

                        }

                    }
                    Column {
                        Spacer(modifier = Modifier.height(50.dp))
                        CalenderCoustemSize(
                            typeOfText = "Start date",
                            date = getPlansResponse.paackage.start_date,
                            painterResource(id = R.drawable.calendar),
                            iconSize = 25,
                            fontsize = 15
                        )
                        CalenderCoustemSize(
                            typeOfText = "End date",
                            date = getPlansResponse.paackage.end_date,
                            painterResource(id = R.drawable.date),
                            iconSize = 25,
                            fontsize = 15
                        )

                    }

                }

            }
        }
    }
}
