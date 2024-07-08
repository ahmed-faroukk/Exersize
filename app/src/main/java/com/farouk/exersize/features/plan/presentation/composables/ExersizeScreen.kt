package com.mala.grad_project.Screenns.subsciptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.features.plan.domain.Entitiy.Exercise
import com.farouk.exersize.features.plan.domain.Entitiy.GetPlanResponse
import com.farouk.exersize.theme.brightYellow
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.LineTextViewChoachScreen
import com.mala.grad_project.newCardExersize.NewCardExersize


@Composable
fun ExerSizeScreen(
    getPlansResponse: GetPlanResponse
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        var selectedDay by remember { mutableStateOf<String?>(null) }
        var CurrentIndex = remember {
            mutableStateOf(0)
        }
        CoachData(
            getPlansResponse.coach.fname,
            lastname = getPlansResponse.coach.lname,
            "${getPlansResponse.paackage.number_of_months}",
            startDate = getPlansResponse.paackage.start_date,
            endDate = getPlansResponse.paackage.end_date,
            painter = null
        )

        Text(
            text = "Current month",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = brightYellow,
            fontWeight = FontWeight.Bold,
        )
        HorizontalDivider()
        val daysOfWeek = getPlansResponse.msg.map { it.day.toLowerCase() to it.day.capitalize() }
        LazyRow {
            itemsIndexed(daysOfWeek) { index, (day, fullName) ->
                WeekDayCard(
                    dayOfWeek = day,
                    fullName = fullName,
                    isSelected = fullName.equals(selectedDay, ignoreCase = true),
                ) {
                    selectedDay = fullName
                }
                CurrentIndex.value = index

            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        LineTextViewChoachScreen(getPlansResponse.msg[CurrentIndex.value].name)

        selectedDay?.let { day ->
            val exercisesForSelectedDay = getPlansResponse.msg
                .find { it.day.equals(day, ignoreCase = true) }
                ?.exercises ?: emptyList()
            DisplayExercisesForDay(selectedDay = day, exercises = exercisesForSelectedDay  )
        }
    }
}




    @Composable
    fun DisplayExercisesForDay(selectedDay: String?, exercises: List<Exercise>  ) {
        selectedDay?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Text(
                    text = "Exercises for $selectedDay",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    items(exercises) { exercise ->
                        NewCardExersize(exersize = exercise )
                    }
                }
            }
        }
    }


