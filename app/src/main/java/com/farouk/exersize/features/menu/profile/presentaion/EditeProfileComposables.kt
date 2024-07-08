package com.farouk.exersize.features.menu.profile.presentaion

import BlueButton
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.farouk.exersize.R
import com.farouk.exersize.features.home.presentaion.composables.BellImage
import com.farouk.exersize.features.inbody.presentation.screens.CircleOutlinePreview
import com.farouk.exersize.features.menu.main.presentatoin.HeadText
import com.farouk.exersize.features.menu.main.presentatoin.OutlineForMobile
import com.farouk.exersize.features.menu.main.presentatoin.OutlineTextFieldToEditData
import com.farouk.exersize.features.menu.profile.domain.Entity.EditeProfileResponse
import com.farouk.exersize.theme.blue1
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.CircleCoachImage
import outlineButton

@Composable
fun EditProfile(
    UpdateProfile : EditeProfileResponse,
    onSave: (String, String, String) -> Unit
) {
    var statfirstname by remember { mutableStateOf(  UpdateProfile.msg.fname) }
    var statlastname by remember { mutableStateOf(UpdateProfile.msg.lname) }
    var statmobile by remember { mutableStateOf(UpdateProfile.msg.phone) }
    var isEditing by remember { mutableStateOf(false) } // State variable to track editing mode
    // Temporary state variables to hold changes during editing
    var tempFirstname by remember { mutableStateOf(statfirstname) }
    var tempLastname by remember { mutableStateOf(statlastname) }
    var tempMobile by remember { mutableStateOf(statmobile) }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
        ,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)){


            Box(
                Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .background(blue1)
                    .padding(top = 30.dp)

            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    CircleCoachImage(painterResource(id = R.drawable.man),55)
                    Text(
                        text = "My Profile",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .height(50.dp)
                            .padding(top = 2.dp)
                    )
                    BellImage(painterResource(id = R.drawable.man))
                }
            }
            Column (
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){


                var photoUri: Uri? by remember { mutableStateOf(null) }
                val launcher =
                    rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                        photoUri = uri
                    }

                val painter = if (photoUri != null) {
                    rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = photoUri)
                            .build()
                    )
                } else {
                    painterResource(id = R.drawable.man)
                }

                CircleOutlinePreview(onclick = {
                    launcher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }, painter = painter)
            }
        }

        //her other code
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp)
        ) {
            if (!isEditing) {
                editWithName(onClick = { isEditing = true })
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()

        ) {
        }
        if(isEditing){
            Spacer(modifier = Modifier.height(20.dp) )
            HeadText(text = "First name")
            OutlineTextFieldToEditData(state = tempFirstname, OnValueChange = {tempFirstname =it},true)
            HeadText(text ="Last name")
            OutlineTextFieldToEditData(state =tempLastname, OnValueChange = {tempLastname=it},true)
            HeadText(text ="Mobile Number")
            OutlineForMobile(state =tempMobile, OnValueChange = {tempMobile =it},true)
        }else{
            Spacer(modifier = Modifier.height(20.dp) )
            HeadText(text = "First name")
            OutlineTextFieldToEditData(state =statfirstname, OnValueChange = {},false)
            HeadText(text ="Last name")
            OutlineTextFieldToEditData(state =statlastname, OnValueChange = {},false)
            HeadText(text ="Mobile Number")
            OutlineForMobile(state =statmobile, OnValueChange = {},false)
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){if (isEditing) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BlueButton(
                    onClick = { isEditing = false
                        statfirstname = tempFirstname
                        statlastname = tempLastname
                        statmobile = tempMobile
                        onSave(statfirstname, statlastname, statmobile) },
                    text = "Save edit",
                    modifier = Modifier
                        .padding(start=15.dp)
                        .weight(1f)
                )
                outlineButton(
                    onClick = { isEditing = false
                        tempFirstname = statfirstname
                        tempLastname = statlastname
                        tempMobile = statmobile
                        isEditing = false },
                    text = "Cancel",
                    modifier = Modifier
                        .padding(end=15.dp ,start=5.dp)
                        .weight(1f)
                )
            }
        }
        }

    }
}



@Composable
fun editWithName(
    onClick: () -> Unit

){
    Box(modifier = Modifier
        .width(160.dp)
        .background(Color.White)
        .padding(5.dp) ) {
        Row (
            horizontalArrangement = Arrangement.Start
        ){
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .padding(start = 5.dp, top = 2.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.edit), contentDescription = null,
                    modifier = Modifier
                        .size(22.dp)
                        .padding(2.dp)
                )
            }

            Box (
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { onClick() }
            ){
                Text(
                    text = "Edit profile",
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = blue1,
                    modifier = Modifier
                        .height(30.dp)
                        .padding(top = 5.dp)
                    ,
                    textDecoration = TextDecoration.Underline
                )
            }

        }
    }
}