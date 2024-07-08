package com.mala.grad_project.Screenns.planCard

import BlueButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.farouk.exersize.LocalTopNavigator
import com.farouk.exersize.features.home.domain.entity.MsgXX
import com.farouk.exersize.features.home.presentaion.composables.CircleCoachImage
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.StarRatingBarCoach
import kotlinx.coroutines.delay

@Composable
fun PackagesUI(
    packages: List<MsgXX>,
    coachName : String ,
    coachImg : String ,
    numOfClints : String ,
    rate : Float,
    onContinueClick:(Int)-> Unit
) {
    val navigator = LocalTopNavigator.current
    var selectedPlan by remember { mutableStateOf(0) }
    println(selectedPlan)
    var rating by remember { mutableStateOf(1f) }

    val card0 = remember {
        mutableStateOf(false)
    }
    val card1 = remember {
        mutableStateOf(false)
    }
    val card2 = remember {
        mutableStateOf(false)
    }
    val card3 = remember {
        mutableStateOf(false)
    }
    val onPlanSelected: (Int , Int) -> Unit = { planId  , index->
        selectedPlan = planId

        when {
            index == 0 -> {
                card0.value = true
                card1.value = false
                card2.value = false
                card3.value = false
            }

            index == 1 -> {
                card0.value = false
                card1.value = true
                card2.value = false
                card3.value = false
            }

            index == 2 -> {
                card0.value = false
                card1.value = false
                card2.value = true
                card3.value = false
            }

            index == 3 -> {
                card0.value = false
                card1.value = false
                card2.value = false
                card3.value = true
            }

        }
    }

    val isVisible = remember {
        mutableStateOf(false)
    }


    val height = remember { mutableStateOf(0.2f) }

    LaunchedEffect(isVisible.value) {
        // Hide the box for 200 milliseconds
        if (!isVisible.value) {
            delay(10)
            isVisible.value = true
        }
        height.value = 50f
    }



        LazyColumn(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            item {

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(height.value.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(start = 10.dp),
                    )



                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(Modifier.fillMaxWidth() ) {
                        
                        CircleCoachImage(rememberAsyncImagePainter(model = coachImg), 120){}
                        Column {
                            Text(
                                text = "Captain: $coachName",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, start = 5.dp),
                                textAlign = TextAlign.Start,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            StarRatingBarCoach(maxStars = 6, rating = rate, onRatingChanged = {
                                rating = it
                            }, paddingStart = 10)

                            Text(
                                text = "number of clients : $numOfClints ",
                                Modifier
                                    .wrapContentSize()
                                    .padding(start = 10.dp),
                                textAlign = TextAlign.Start,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.surface
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                      
                        
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                LineTextViewPlanScreen("Choose the package that suits you")
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    item {

                        PlanCard(
                            listOfPackage = packages[0],
                            onPlanSelected = onPlanSelected,
                            isSelected = card0,
                            0
                        )
                        PlanCard(
                            listOfPackage = packages[1],

                            onPlanSelected = onPlanSelected,
                            isSelected = card1
                            ,1
                        )
                    }
                }
                LazyRow(

                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    item {
                        PlanCard(
                            listOfPackage = packages[2],
                            onPlanSelected = onPlanSelected,
                            isSelected = card2
                            ,2
                        )
                        PlanCard(
                            listOfPackage = packages[3],
                            onPlanSelected = onPlanSelected,
                            isSelected = card3
                            ,3
                        )
                    }

                }
            }
            item {

                Spacer(modifier = Modifier.height(10.dp))
                BlueButton(onClick = {
                    onContinueClick(selectedPlan)
                }, modifier = Modifier.width(150.dp), text = "Continue")
                Spacer(modifier = Modifier.height(30.dp))

            }


        }
    }


