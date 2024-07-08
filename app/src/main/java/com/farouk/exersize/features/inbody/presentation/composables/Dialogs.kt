package com.farouk.exersize.features.inbody.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.farouk.exersize.base.composables.RoundedBtn
import com.farouk.exersize.features.authentication.presentation.components.AFLoading
import com.farouk.exersize.features.authentication.presentation.components.AuthText
import com.farouk.exersize.features.inbody.presentation.UserInBodyState
import com.farouk.exersize.theme.blue2
import com.farouk.exersize.theme.blue3
import com.farouk.exersize.theme.darkYellow

@JvmOverloads
@Composable
fun InBodyErrorDialog(
    onDismissRequest: () -> Unit,
    title: String,
    desc: String
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        blue3,
                        shape = RoundedCornerShape(16.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AuthText(
                        text = title ,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp), sizeWithSp = 15
                    )
                    AuthText(
                        text = desc,
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp) , sizeWithSp = 13,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss" , color = darkYellow)
                    }
                }
            }
        }
    }
}

@Composable
fun InBodyLoadingDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        // onDismissRequest =  LoadingDialog.value = false
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AFLoading(
                    color1 = MaterialTheme.colorScheme.primary,
                    color2 = MaterialTheme.colorScheme.primary,
                    color3 = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@JvmOverloads
@Composable
fun InBodyInfoDialog(
    title: String = "Message",
    desc: String = "Your Message",
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = Color.Transparent,
                )
        ) {


            Box(
                modifier = Modifier
                    .background(
                        color = blue2,
                        shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                    )
                    .align(Alignment.Center),
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    //.........................Text: title
                    Text(
                        text = title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = darkYellow,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = darkYellow,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button
                    RoundedBtn(
                        onClick = { onDismiss() },
                        text = "ok",
                        textColor = MaterialTheme.colorScheme.surface,
                        buttonColor = darkYellow
                    )


                    //.........................Spacer
                    Spacer(modifier = Modifier.height(30.dp))

                }


            }

        }
    }
}
@Composable
fun ShowInBodyErrorDialog(state: UserInBodyState, errorDialog: MutableState<Boolean>) {
    if (errorDialog.value) {
        InBodyErrorDialog(onDismissRequest = {
            errorDialog.value = false
        }, title = "Server Error", desc = state.error)
    }
}

@Composable
fun ShowInBodyInfoDialog(msg: String, infoDialog: MutableState<Boolean>) {
    if (infoDialog.value) {
        InBodyInfoDialog(
            title = "Login Problem",
            desc = msg
        ) {
            infoDialog.value = false
        }
    }
}

