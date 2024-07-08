package com.farouk.exersize.features.home.presentaion.getCoach.Screen

import CoachScreenContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.farouk.exersize.LocalTopNavigator
import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.authentication.presentation.components.AFLoading
import com.farouk.exersize.features.authentication.presentation.components.ErrorDialog
import com.farouk.exersize.features.home.presentaion.HomeViewModel
import com.farouk.exersize.features.home.presentaion.Packages.PackagesScreen

class CoachDetails(private val id : String , private val rate : Float) : Screen {

    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = getViewModel()
        CoachDetailsUi(viewModel, id)
    }

    @Composable
    fun CoachDetailsUi(viewModel: HomeViewModel, id: String) {
        val navigator = LocalTopNavigator.current

        println("--------------------------------------------------------------------------------------------------")
        LaunchedEffect(key1 = Unit) {
            viewModel.getCoach(id)
        }
        println(id)
        val dialog = remember {
            mutableStateOf(false)
        }
        //viewModel.getCoachPortfolio(id)
        val coach = viewModel.getCoach.value
        when {
            coach.isLoading -> {
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

            coach.data?.status == false -> {
                dialog.value = true
            }

            coach.error.isNotEmpty() -> {
                dialog.value = true
            }

            dialog.value -> {
                ErrorDialog(onDismissRequest = {
                    dialog.value = false
                }, title = "Error", desc = coach.error.toString())
            }
        }
        coach.data?.let {
            CoachScreenContent(rate, it) {
                navigator.replace(
                    PackagesScreen(
                        id,
                        "${it.msg.fname} ${it.msg.lname}",
                        "${BASE_URL}${it.msg.personal_img}",
                        it.msg.exp.toString() ,
                        it.msg.trainees_number.toString(),
                        rate
                    )
                )
            }
        }
    }
}