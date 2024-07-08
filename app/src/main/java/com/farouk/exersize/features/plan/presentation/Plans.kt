package com.farouk.exersize.features.plan.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.presentation.components.AFLoading
import com.farouk.exersize.features.authentication.presentation.components.ErrorDialog
import com.farouk.exersize.features.home.presentaion.HomeTab.HomeTab
import com.farouk.exersize.features.menu.presentatoin.ChatTab
import com.mala.grad_project.Screenns.Home.screen.RetryIcon
import com.mala.grad_project.Screenns.subsciptions.BlueButton
import com.mala.grad_project.Screenns.subsciptions.PlanUIScreen

object PlansTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Plan"
            val icon = painterResource(R.drawable.plan)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: PlansViewModel = getViewModel()
        val state = viewModel.traineePlanState.value
        val tabNavigator = LocalTabNavigator.current

        val dialog = remember {
            mutableStateOf(false)
        }

        when {
            state.isLoading -> {
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
            state.data?.status == false -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RetryIcon {
                        viewModel.getTraineePlans()
                    }

                        Text(text = "you are not subscribed with any coach please go to subscribe first to get your plan " , textAlign = TextAlign.Center)

                        BlueButton(onClick = {
                            tabNavigator.current = HomeTab

                        }, text = "Subscribe Now " )

                    }
                }
            state.data?.status== true ->  {
                if(state.data.msg.isEmpty() && state.data.payment_status == "COMPLETED") {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .background(MaterialTheme.colorScheme.primary),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RetryIcon {
                            viewModel.getTraineePlans()
                        }

                        Text(text = "the coach dose not add any plans yet ask him to add one in chat  " , textAlign = TextAlign.Center)

                        BlueButton(onClick = {
                            tabNavigator.current = ChatTab

                        }, text = "chat with coach " )

                    }
                }
                else {
                    PlanUIScreen(getPlansResponse = state.data, viewModel  )
                }

            }


            state.errorMsg.isNotEmpty() -> {
                dialog.value = true
            }

            dialog.value -> {
                ErrorDialog(onDismissRequest = {
                    dialog.value = false
                }, title = "Error", desc = state.errorMsg.toString())
            }
            else->{
                RetryIcon {
                    viewModel.getTraineePlans()
                }
            }
        }




    }
}