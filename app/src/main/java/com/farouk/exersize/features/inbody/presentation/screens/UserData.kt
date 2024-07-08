package com.farouk.exersize.features.inbody.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.darkYellow


//step two
@Composable
fun UserDataScreen(
    age : MutableState<String> = remember {
    mutableStateOf("")
},
    weight : MutableState<String> = remember {
    mutableStateOf("")
},
    tall : MutableState<String> = remember {
    mutableStateOf("")
},

) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
    ) {
        Spacer(Modifier.height(30.dp))

        OwenTextField(
            state = age,
            onValueChange = { age.value = it },
            holder = "Enter your age",
            text = "What is your age?", isError = age.value.isEmpty()
        )
        Spacer(Modifier.height(30.dp))
        OwenTextField(
            state = weight,
            onValueChange = { weight.value = it },
            holder = "Enter your weight",
            text = "What is your Weight?",
            isError = weight.value.isEmpty()
        )
        Spacer(Modifier.height(30.dp))
        OwenTextField(
            state = tall,
            onValueChange = { tall.value = it },
            holder = "Enter your tall",
            text = "What is your tall?",
            isError = tall.value.isEmpty()
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwenTextField(
    state: MutableState<String> = remember {
        mutableStateOf("")
    },
    onValueChange: (String) -> Unit,
    holder: String,
    text: String,
    isError: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val indicatorWidth = 1.9.dp
        var indicatorColor = darkYellow
        val textFieldPadding = 5.dp
        Text(
            modifier = Modifier.padding(bottom = 5.dp),
            text = text,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.surface,
            fontWeight = FontWeight.Bold
        )
        TextField(
            value = state.value,
            onValueChange = {
                onValueChange(it)
            },
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
            placeholder = { Text(text = holder, textAlign = TextAlign.Start, color = blue1) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

            )
        if (isError) {
            Text(
                text = "*required ",
                fontSize = 12.sp,
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp),
                overflow = TextOverflow.Visible,

                )
            indicatorColor = Color.Red
        }

    }
}