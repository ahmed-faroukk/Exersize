package com.farouk.exersize.features.inbody.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.R
import com.farouk.exersize.theme.blue1

enum class Gender {
    Male,
    Female,
    Unspecified
}

// gender Screen
// step one
@Composable
fun GenderScreen(
     selectedGender : MutableState<Gender> = remember { mutableStateOf(Gender.Unspecified) }
) {

    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
        , horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(Modifier.height(30.dp))
        Text(
            text = "What is your gender?",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold ,
            style = TextStyle(color = MaterialTheme.colorScheme.surface)
        )
        Spacer(Modifier.height(30.dp))
        CardOfGender(painter = painterResource(id = R.drawable.boy), type = "Male",isSelected =selectedGender.value==Gender.Male){
            selectedGender.value = Gender.Male


        }
        Spacer(Modifier.height(30.dp))
        CardOfGender(painter = painterResource(id = R.drawable.woman), type="Female",isSelected = selectedGender.value == Gender.Female){
            selectedGender.value =Gender.Female

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardOfGender(painter: Painter, type:String, isSelected:Boolean, onSelected:()->Unit){
    var backColor by remember { mutableStateOf(Color.White) }
    var textColor by remember { mutableStateOf(blue1) }

    Card(
        modifier = Modifier.size(height =200.dp, width = 180.dp),
        colors = CardDefaults.cardColors(
            backColor
        ),
        elevation = CardDefaults.cardElevation(
            15.dp
        ), onClick = {
            onSelected()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = "error image",
                modifier = Modifier.size(150.dp)
            )
            Text(
                text = type,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
    LaunchedEffect(isSelected){
        if(isSelected){
            backColor= blue1
            textColor= Color.White
        }
        else{
            backColor= Color.White
            textColor= blue1
        }
    }
}