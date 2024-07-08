package com.farouk.exersize.features.chat.presentatoin.composables

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.presentation.components.OutLineTextFieldString
import com.farouk.exersize.features.chat.domain.Entity.MsgX
import com.farouk.exersize.features.chat.presentatoin.ChatViewModel
import com.farouk.exersize.features.home.presentaion.HomeTab.HomeTab
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.blue2
import com.farouk.exersize.theme.brightYellow
import com.farouk.exersize.theme.darkYellow
import com.farouk.exersize.theme.sender
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.CircleCoachImage

enum class Sender {
    COACH, TRAINEE
}


@Composable
fun TraineeMsg(msg: String, date: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, end = 10.dp, start = 50.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .background(brightYellow, RoundedCornerShape(90.dp))
        ) {
            Text(
                text = msg,
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier.padding(15.dp)
            )
        }
        Text(
            text = date,
            style = TextStyle(fontSize = 10.sp, color = Color.DarkGray),
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun CoachMsg(msg: String, date: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, start = 10.dp, end = 50.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .background(sender, RoundedCornerShape(90.dp))
        ) {
            Text(
                text = msg,
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier.padding(15.dp)
            )
        }
        Text(
            text = date,
            style = TextStyle(fontSize = 10.sp, color = Color.DarkGray),
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun MsgContainer() {

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatUI(viewModel: ChatViewModel, messages : List<MsgX>,onclick: () -> Unit) {
    val message = remember {
        mutableStateOf("")
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(bottom = 65.dp),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(blue1)
                .weight(2f)
        ) {
            header()
        }
        Box(
            Modifier
                .fillMaxWidth()
                .weight(7.5f)
                .background(blue1)
        ) {
            ChatContainer(viewModel , messages)

        }

        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .weight(1f), verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedCardWithTextAndButton(value = message.value, onValueChange = {
                message.value = it
            }) {

                viewModel.addMsg(message.value)
                viewModel.sendMsg(message.value)
                message.value = ""
                onclick()


            }


        }
    }


}

@Composable
fun ChatEditeText(message: MutableState<String>) {
    AddFilesIcon {

    }
    OutLineTextFieldString(onNameChange = { text ->
        // Update the state in InputWrapper when the text changes
        message.value = text
    }, label = "Type Message")
}

@Composable
fun AddFilesIcon(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.addfile_icon),
        contentDescription = "My Icon",
        Modifier
            .size(25.dp)
            .clickable {
                onClick()
            }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DefaultLocale")
@Composable
fun ChatContainer(viewModel: ChatViewModel , messages : List<MsgX>) {
    val chatListState = rememberLazyListState()

    LaunchedEffect(key1 = messages.size) {
        if (messages.size>0)
        chatListState.scrollToItem(messages.size - 1)
    }

    LazyColumn(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(start = 5.dp, end = 5.dp), state = chatListState
    ) {
        items(messages) { message ->
            when (message.sender) {
                Sender.COACH.toString().toLowerCase() -> CoachMsg(
                    msg = message.content,
                    date = message.created_at
                )

                Sender.TRAINEE.toString().toLowerCase() -> TraineeMsg(
                    msg = message.content,
                    date = message.created_at
                )
            }
        }
    }
}

@Composable
fun header(
    coachImg: Painter = painterResource(id = R.drawable.coach),
    coachName: String = "Ahmed Farouk",
    state: String = "Online",
) {
    val tabNavigator = LocalTabNavigator.current
    Box(
        Modifier
            .fillMaxWidth()
            .padding(10.dp), contentAlignment = Alignment.TopEnd
    ) {
        com.farouk.exersize.features.home.presentaion.composables.CircleCoachImage(
            painterResource(id = R.drawable.backto),
            10,
            initSize = 10f,
            targetSize = 30f,
        ) {
            tabNavigator.current = HomeTab
        }
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
    ) {
        CircleCoachImage(painter = coachImg, size = 80)
        Column {
            Text(
                text = coachName,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 15.dp),
                color = darkYellow
            )
            Text(
                text = state,
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier.padding(start = 15.dp, top = 3.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun OutlinedCardWithTextAndButton(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier.padding(5.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Blue,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White,
        )
    ) {
        val context = LocalContext.current

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                        intent.type = "*/*"
                        intent.addCategory(Intent.CATEGORY_OPENABLE)
                        context.startActivity(intent)
                    },
                    backgroundColor = darkYellow,
                    contentColor = Color.White,
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(Icons.Filled.Add, "", tint = Color.Black)
                }

                BasicTextField(
                    cursorBrush = SolidColor(blue2),
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f)
                        .background(Color.White)
                        .widthIn(40.dp),
                    textStyle = TextStyle(color = Color.Black),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.padding(bottom = 5.dp)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = "Type Message",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(start = 8.dp),
                                    textAlign = TextAlign.Left
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { if (value.isNotEmpty()) onClick() },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(Icons.Filled.Send, "", tint = Color.Black)
                }
            }
        }
    }
}


@Composable
fun lineWitheSend(
    onclick: () -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .padding(top = 5.dp, start = 5.dp, end = 10.dp)
            .fillMaxWidth(),
        Arrangement.End
    ) {
        VerticalLineSmall(
            color = Color.Gray,
            height = 30.dp,
            width = 1.dp,
            modifier = Modifier.padding(end = 3.dp)
        )
        FloatingActionButton(
            onClick = { onclick() },
            backgroundColor = darkYellow,
            contentColor = Color.White,
            modifier = Modifier.size(30.dp)
        ) {
            Icon(Icons.Filled.Send, "", tint = Color.Black)
        }

    }
}

@Composable
fun VerticalLineSmall(
    color: Color,
    height: Dp,
    width: Dp,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(color)
    )

}