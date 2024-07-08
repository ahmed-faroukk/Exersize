package com.farouk.exersize.features.home.presentaion.reqPackage

import BlueButton
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
import com.farouk.exersize.features.authentication.presentation.components.AFLoading
import com.farouk.exersize.features.authentication.presentation.components.ErrorDialog
import com.farouk.exersize.features.home.presentaion.HomeViewModel
import com.farouk.exersize.features.inbody.presentation.screens.GifImage
import com.farouk.exersize.theme.darkYellow

class ReqPackagesScreen( private val coachId : String , private val packageId : String) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalTopNavigator.current

        val viewmodel: HomeViewModel = getViewModel()
        val req = viewmodel.reqPackages.value
        LaunchedEffect(key1 = Unit) {
            viewmodel.reqPackages(coachId , packageId)
        }

        val dialog = remember {
            mutableStateOf(false)
        }

        when {
            req.data?.status == false ->{
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ErrorDialog(onDismissRequest = {
                        navigator.pop()
                    }, title = "Can not buy this package ", desc = "you have a package already with this coach try again after finish your subscription ")
                }
            }
            req.data?.status == true ->{
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GifImage()
                    BlueButton(onClick = { navigator.pop() }, text = "back to home " , color = darkYellow)
                }
            }
            req.isLoading -> {
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

            req.error.isNotEmpty() -> {
                dialog.value = true
            }

            dialog.value -> {
                ErrorDialog(onDismissRequest = {
                    dialog.value = false
                }, title = "Error", desc = req.error.toString())
            }
        }


    }
}
