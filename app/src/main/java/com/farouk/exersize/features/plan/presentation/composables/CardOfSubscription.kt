package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.farouk.exersize.theme.darkYellow

@Composable
fun CardSubscriptionPending(
    showPlansExercises: GetPlanResponse
){
    Box (
        Modifier
            .padding(10.dp)
            .shadow(elevation = 10.dp)
            .clickable {

            }
    ){


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(5.dp))
            ,
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    Text(
                        text ="Captain: ",

                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 15.sp
                    )
                    Text(
                        text ="${showPlansExercises.coach.fname} ${showPlansExercises.coach.lname}",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 15.sp
                    )
                }
                Row{
                    Text(

                        text ="Package: ",
                        modifier = Modifier.padding(top=5.dp),
                        fontSize = 15.sp,
                        color = darkYellow ,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text ="${showPlansExercises.paackage.number_of_months} Months",
                        modifier = Modifier.padding(top=5.dp),
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.surface,
                       fontWeight = FontWeight.Bold

                    )
                }
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showPlansExercises.payment_status == "ACCEPTED"){
                        Image(
                            painter = painterResource(id = R.drawable.payment),
                            contentDescription = null,
                            Modifier
                                .size(20.dp)
                                .padding(top = 2.dp)

                        )

                        Column {

                                Text(
                                    text = "Pending",
                                    fontSize = 10.sp,
                                    color = darkYellow ,
                                    modifier = Modifier.padding( top=9.dp)

                                )
                            Text(
                                text = "Please pay the subscription cost",
                                fontSize = 8.sp,
                                color = Color.Red,
                                modifier = Modifier.padding(start = 5.dp, top=5.dp)

                            )

                        }
                    }
                }

            }
            Column {
                Calender(typeOfText = "Start date", date =showPlansExercises.paackage.start_date, painterResource(id = R.drawable.calendar), iconSize = 20, fontsize =10 )
                Calender(typeOfText = "End date", date =showPlansExercises.paackage.end_date, painterResource(id = R.drawable.date), iconSize = 20, fontsize = 10 )

            }

        }

    }
    }
}
