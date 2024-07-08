package com.farouk.exersize.features.inbody.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.R
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.darkYellow


private fun getFileName(uri: Uri): String {
    return uri.pathSegments.lastOrNull() ?: "Unknown"
}

//step3
@Composable
fun UploadInBodyScreen(
    fileName : MutableState<String>,
    selectedFileUri : MutableState<Uri> = remember { mutableStateOf(Uri.EMPTY) },
    onSkip: () -> Unit
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedFileUri.value = uri
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
    ) {

        OwenTextFieldWithUploadIcon(
            fileName.value.let { getFileName(selectedFileUri.value) } ?: fileName.value,
            {
                fileName.value = it
            },
            "Upload your in-body ",
            "Upload your in-body",
            onclick = {
                launcher.launch("application/*")
            })
        Text(
            modifier = Modifier
                .clickable { onSkip() },
            text = "Skip now",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            color = MaterialTheme.colorScheme.surface,
            textDecoration = TextDecoration.Underline
        )
    }
}


@Composable
fun OwenTextFieldWithUploadIcon(
    state: String,
    onValueChange: (String) -> Unit,
    holder: String,
    text: String,
    onclick: ()->Unit
) {
    Column(

        modifier = Modifier.fillMaxWidth()
    ) {

        val indicatorWidth = 1.9.dp
        val indicatorColor = darkYellow
        val textFieldPadding = 5.dp
        Text(modifier = Modifier.padding(bottom =5.dp),
            text = text,
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold)
        TextField(
            value =state ,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 30.dp)
                .drawWithContent {
                    drawContent()
                    val strokeWidth = indicatorWidth.value * density
                    val y = size.height - strokeWidth / 2
                    val cornerRadius = 50.dp.toPx()
                    drawRoundRect(
                        color = indicatorColor,
                        topLeft = Offset(textFieldPadding.toPx(), y - strokeWidth / 2),
                        size = Size(size.width - 2 * textFieldPadding.toPx(), strokeWidth),
                        cornerRadius = CornerRadius(cornerRadius),
                        style = Stroke(width = strokeWidth)
                    )
                },
            singleLine = true,
            placeholder = { Text(text = holder, textAlign = TextAlign.Start, color = darkYellow) },
            shape = RoundedCornerShape(13.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = blue1,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedLabelColor = blue1,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor =  MaterialTheme.colorScheme.primary,
                unfocusedTextColor = darkYellow,
                focusedTextColor = MaterialTheme.colorScheme.surface
            ),
            readOnly = true,
            trailingIcon  = { Image(
                painterResource(id = R.drawable.upload )
                , contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        onclick()
                    },
            ) },
        )


    }
}