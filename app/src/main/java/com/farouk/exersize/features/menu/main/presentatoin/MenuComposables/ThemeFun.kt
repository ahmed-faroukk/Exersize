package com.mala.grad_project.Screenns.Menu

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.R
import com.farouk.exersize.theme.darkYellow

@Composable
fun Theme(
    onSwitchClicked: () -> Unit,
    onThemeClicked: () -> Unit
) {
    var checked by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("Light Mode") }

    Box(
        modifier = Modifier
            .background(color = Color.Transparent)
            .fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 10.dp, top = 5.dp),
                painter = painterResource(id = R.drawable.bell),
                contentDescription = null
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Theme",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 5.dp)
                        .clickable(onClick = onThemeClicked),
                    textAlign = TextAlign.Start
                )
                Row(Modifier.padding(end = 20.dp)) {
                    text = if (checked == true) "Dark Mode" else "Light Mode"
                    Text(
                        text = text,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkYellow,
                        modifier = Modifier
                            .padding(top = 15.dp, start = 20.dp)
                            .clickable(onClick = onThemeClicked),
                        textAlign = TextAlign.Start
                    )

                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                            onSwitchClicked()
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = darkYellow,
                            checkedTrackColor = Color.Black,
                            uncheckedThumbColor = darkYellow,
                            uncheckedTrackColor = Color.LightGray
                        )
                    )
                }
            }
        }
        Canvas(modifier = Modifier.matchParentSize().padding(start = 15.dp, end = 15.dp)) {
            drawLine(
                color = darkYellow,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 5f
            )
        }
    }
}

