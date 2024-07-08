package com.mala.grad_project.Screenns.Home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.LocalTopNavigator
import com.farouk.exersize.R
import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.authentication.presentation.components.AFLoading
import com.farouk.exersize.features.authentication.presentation.components.ErrorDialog
import com.farouk.exersize.features.home.presentaion.HomeViewModel
import com.farouk.exersize.features.home.presentaion.composables.BellImage
import com.farouk.exersize.features.home.presentaion.composables.CoachCard
import com.farouk.exersize.features.home.presentaion.getCoach.Screen.CoachDetails
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeUI(
    viewModel: HomeViewModel,
) {

    val isVisible = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isVisible.value) {
        // Hide the box for 200 milliseconds
        if (!isVisible.value) {
            delay(1)
            isVisible.value = true
        }
    }

    val navigator = LocalTopNavigator.current

    val coaches = viewModel.getAllCoaches.value

    val dialog = remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        when {
            coaches.isLoading -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AFLoading(
                        color1 = MaterialTheme.colorScheme.surface,
                        color2 = MaterialTheme.colorScheme.surface,
                        color3 = MaterialTheme.colorScheme.surface
                    )
                }

            }

            coaches.error.isNotEmpty() -> {
                dialog.value = true
            }

            dialog.value -> {
                ErrorDialog(onDismissRequest = {
                    dialog.value = false
                }, title = "Error", desc = coaches.error.toString())
            }
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
              ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HomeHeader(viewModel , isVisible)
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth(),
            visible = isVisible.value,
            enter = slideInVertically(
                initialOffsetY = { 1000 }, animationSpec = tween(2000)
            ) + fadeIn(),
        ) {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                coaches.data?.let {
                    items(it.msg) {coach ->
                        CoachCard(
                            clintNum = coach.trainees_number.toString(),
                            name = "${coach.fname} ${coach.lname}",
                            img = BASE_URL + coach.personal_img ,
                            coach.rate ,
                            modifier = Modifier.animateItemPlacement()
                        ) {
                            navigator.push(CoachDetails(coach.id.toString() , coach.rate))
                        }

                    }
                }
            }
        }

    }
}

@Composable
fun HomeHeader(
    viewModel: HomeViewModel ,
    isVisible : MutableState<Boolean>
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(190.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 50.dp, start = 10.dp, end = 10.dp)
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                RetryIcon {
                    viewModel.getAllCoaches()
                    isVisible.value = false
                }
                BellImage(painterResource(id = R.drawable.bell))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Choose the right coach for you",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 15.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}


@Composable
fun RetryIcon(
    size: Dp = 50.dp,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(targetValue = if (onClick != null) 0f else 360f, label = "")

    Icon(
        imageVector = Icons.Filled.Refresh,
        contentDescription = "Retry",
        tint = Color.Red, // Change color as needed
        modifier = Modifier
            .size(size)
            .padding(8.dp)
            .clickable(onClick = onClick)
            // Rotate the icon based on the animation state
            .rotate(rotation)
    )
}