package com.farouk.exersize.base.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.presentation.components.AuthText
import com.farouk.exersize.theme.blue3
import com.farouk.exersize.theme.darkYellow


@Composable
fun InfoDialog(
    title: String? = "Message",
    desc: String? = "Your Message",
    imgId: Int = R.drawable.nointernet,
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
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                    )
                    .align(Alignment.BottomCenter),
            ) {

                Image(
                    painter = painterResource(id = imgId),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(),

                    )
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Text: title
                    Text(
                        text = title!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 130.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    //.........................Text : description
                    Text(
                        text = desc!!,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                    //.........................Button : OK button
                    RoundedBtn(
                        onClick = { onDismiss() },
                        text = "ok",
                        textColor =MaterialTheme.colorScheme.surface,
                        buttonColor = MaterialTheme.colorScheme.primary
                    )


                    //.........................Spacer
                    Spacer(modifier = Modifier.height(24.dp))

                }


            }

        }
    }
}
@Composable
fun RoundedBtn(
    onClick: () -> Unit,
    text: String,
    textColor: Color = blue3,
    buttonColor: Color = darkYellow,
    modifier: Modifier = Modifier,
    textSizeSp: Int = 15,
) {

    Button(
        onClick = {
            onClick()
        }, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
            buttonColor
        ), modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp, top = 10.dp)
    ) {
        AuthText(
            text = text,
            color = textColor,
            fontWeight = FontWeight.ExtraBold,
            sizeWithSp = textSizeSp,
            textAlign = TextAlign.Center
        )
    }
}
