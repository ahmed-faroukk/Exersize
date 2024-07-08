package com.farouk.exersize.features.inbody.presentation.steper

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import com.farouk.exersize.features.inbody.presentation.InBodyViewModel
import com.farouk.exersize.features.inbody.presentation.composables.InBodyErrorDialog
import com.farouk.exersize.features.inbody.presentation.composables.InBodyLoadingDialog
import com.farouk.exersize.features.inbody.presentation.composables.ShowInBodyErrorDialog
import com.farouk.exersize.features.inbody.presentation.composables.ShowInBodyInfoDialog
import com.farouk.exersize.features.inbody.presentation.screens.Gender
import com.farouk.exersize.features.inbody.presentation.screens.GenderScreen
import com.farouk.exersize.features.inbody.presentation.screens.UploadImageScreen
import com.farouk.exersize.features.inbody.presentation.screens.UploadInbodyScreen
import com.farouk.exersize.features.inbody.presentation.screens.UserDataScreen
import com.farouk.exersize.theme.ExersizeTheme
import com.farouk.exersize.theme.darkYellow

class StepperScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel: InBodyViewModel = getViewModel()
        InBodyStepper(viewModel)

    }

    @Composable
    fun InBodyStepper(
        viewModel: InBodyViewModel
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween

        ) {

            val numberStep = 4
            var currentStep by rememberSaveable { mutableStateOf(1) }
            val titleList = arrayListOf("Gender", "Personal Data", "InBody", "Profile Pic")
            val userGender = remember {
                mutableStateOf(Gender.Unspecified)
            }

            val inBodyPdfPath = remember {
                mutableStateOf("")
            }
            val selectedPdfUri: MutableState<Uri> = remember { mutableStateOf(Uri.EMPTY) }

            val photoUri: MutableState<Uri?> = remember {
                mutableStateOf(null)
            }
            val inBodyImgUri: MutableState<Uri?> = remember {
                mutableStateOf(null)
            }

            val allowToMove = remember {
                mutableStateOf(false)
            }
            val showDialog = remember {
                mutableStateOf(false)
            }
            val age = remember {
                mutableStateOf("")
            }
            val weight = remember {
                mutableStateOf("")
            }
            val tall = remember {
                mutableStateOf("")
            }
            val errorDialog = remember {
                mutableStateOf(false)
            }
            val infoDialog = remember {
                mutableStateOf(false)
            }

            val reqIsSent = remember {
                mutableStateOf(false)
            }

            val navigator = LocalNavigator.currentOrThrow
            val context = LocalContext.current

            val state = viewModel.inBodyState.value
            when {

                state.data?.status == true && !reqIsSent.value  -> {
                    viewModel.navigateToHome(navigator = navigator)
                    reqIsSent.value = true
                }
                state.data?.status == false ->{
                    currentStep = 1
                }

                state.isLoading -> {
                    InBodyLoadingDialog(onDismissRequest = {})
                    errorDialog.value = true
                }

                state.error.isNotEmpty() -> {
                    ShowInBodyErrorDialog(state, errorDialog)
                }

                state.data?.error_msg?.isNotEmpty() == true -> {
                    ShowInBodyInfoDialog(state.data.error_msg, infoDialog)
                }
            }


            Stepper(
                modifier = Modifier.fillMaxWidth(),
                numberOfSteps = numberStep,
                currentStep = currentStep,
                stepDescriptionList = titleList,
                unSelectedColor = darkYellow,
                selectedColor = MaterialTheme.colorScheme.surface,
            )
            if (showDialog.value)
                InBodyErrorDialog({
                    showDialog.value = false
                } , title = "Can't go Next", desc = "Please Fill Your Data First")

            when (currentStep) {
                1 -> {
                    GenderScreen(userGender)
                    allowToMove.value = userGender.value != Gender.Unspecified
                }

                2 -> {
                    UserDataScreen(age, weight, tall)
                    allowToMove.value = age.value.isNotEmpty() && weight.value.isNotEmpty() && tall.value.isNotEmpty()
                }

                3 -> {
                    allowToMove.value = true
                  /*  UploadInBodyScreen(inBodyPdfPath, selectedPdfUri) {
                        currentStep++
                    }*/
                    UploadInbodyScreen(
                        inBodyImgUri
                    )
                }

                4 -> {
                    UploadImageScreen(
                        photoUri
                    )
                    allowToMove.value = photoUri.value != null
                }


            }
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = { if (currentStep > 1) currentStep-- },
                    enabled = currentStep > 1,
                ) {
                    Text(text = "previous")
                }

                Button(
                    onClick = {
                        if (currentStep <= numberStep)
                            when {
                                allowToMove.value -> {
                                    currentStep++
                                    allowToMove.value = false
                                }

                                !allowToMove.value -> {
                                    showDialog.value = true
                                }
                            }
                        if (currentStep == 5) {
                            Log.d("gender : ", userGender.value.toString())
                            Log.d("tall : ", tall.value.toString())
                            Log.d("age : ", age.value.toString())
                            Log.d("weight : ", weight.value.toString())
                            Log.d("pdf Path : ", selectedPdfUri.value.toString())
                            Log.d("img Path : ", photoUri.value.toString())
                            photoUri.value?.let {
                                inBodyImgUri.value?.let { it1 ->
                                    viewModel.sendInBodyData(
                                        gender = userGender.value.toString(),
                                        age = age.value,
                                        weight = weight.value,
                                        tall = tall.value,
                                        token = viewModel.token.value,
                                        inBodyFilePath = it1,
                                        imgFilePath = it,
                                        context = context,
                                        navigator
                                    )
                                }
                            }
                            currentStep--

                        }
                    },
                    enabled = currentStep <= numberStep,
                    colors = ButtonDefaults.buttonColors(darkYellow)
                ) {
                    Text(
                        text = "next",
                        style = TextStyle(color = MaterialTheme.colorScheme.surface)
                    )
                }

            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExersizeTheme {
        Navigator(
            screen = StepperScreen(),
            content = { navigator -> SlideTransition(navigator) })
    }
}