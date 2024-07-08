package com.mala.grad_project.Screenns.Menu

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.farouk.exersize.R
import com.farouk.exersize.features.home.presentaion.HomeTab.HomeTab
import com.farouk.exersize.features.home.presentaion.composables.BellImage
import com.farouk.exersize.features.home.presentaion.composables.CircleCoachImage

@Composable
fun MenuScreen(
logoutClick :() -> Unit ,
aboutUsClick :() -> Unit ,
onSwitchClick :() -> Unit ,
onThemeClick :() -> Unit ,

){
    val tabNavigator = LocalTabNavigator.current

    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(MaterialTheme.colorScheme.surface)

        ){
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    CircleCoachImage(painterResource(id = R.drawable.backto),10  , initSize = 10f , targetSize = 50f){
                        tabNavigator.current = HomeTab
                    }
                    Text(
                        text ="menu",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color=Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 10.dp),
                        textAlign = TextAlign.Center
                    )
                    BellImage(painterResource(id = R.drawable.bell))
                }

            }

        }
        Spacer(modifier = Modifier.height(30.dp))
        MenuSettings(text="My profile", painter = painterResource(id = R.drawable.user), onClick = {})
        Spacer(modifier = Modifier.height(15.dp))
        Theme(
            onSwitchClicked = {
                onSwitchClick()
            },
            onThemeClicked = {
                onThemeClick()
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        MenuSettings(text="About us", painter = painterResource(id = R.drawable.informationbutton), onClick = {
            aboutUsClick()
        })
        Spacer(modifier = Modifier.height(15.dp))
        MenuSettings(text="Log out", painter = painterResource(id = R.drawable.logout), onClick = {
            logoutClick()
        })
        Spacer(modifier = Modifier.height(15.dp))

    }

}