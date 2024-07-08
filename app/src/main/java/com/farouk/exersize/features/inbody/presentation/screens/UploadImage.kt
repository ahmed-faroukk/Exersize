package com.farouk.exersize.features.inbody.presentation.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.farouk.exersize.R
import com.farouk.exersize.theme.darkYellow


// step 4
@Composable

fun UploadImageScreen(
    uri: MutableState<Uri?>

) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { URI ->
        uri.value = URI
    }

    val painter = if (uri.value != null) {
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = uri.value)
                .build()
        )
    } else {
        painterResource(id = R.drawable.baseline_image_24)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))

        CircleOutlinePreview(onclick = {
            launcher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
            , painter = painter)

    }
}
@Composable
fun UploadInbodyScreen(
    uri: MutableState<Uri?>

) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { URI ->
        uri.value = URI
    }

    val painter = if (uri.value != null) {
        rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = uri.value)
                .build()
        )
    } else {
        painterResource(id = R.drawable.baseline_image_24)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))

        Text(text = "Upload your inBody")
        CircleOutlinePreview(onclick = {
            launcher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
            , painter = painter)

    }
}
@Composable
fun CircleOutlinePreview(  onclick:()->Unit,painter: Painter
) {

    Box (contentAlignment = Alignment.Center){

        val borderWidth = 1.dp
        Image(
            painter = painter,
            contentDescription = "error image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(230.dp)
                .border(
                    BorderStroke(borderWidth, darkYellow),
                    CircleShape
                )
                .padding(borderWidth)
                .clip(CircleShape)
        )
        IconOfImageProfile(onclick)
    }
}
@Composable
fun IconOfImageProfile(
    onclick:()->Unit
){
    Box(modifier = Modifier
        .width(200.dp)
        .padding(top = 150.dp, end = 160.dp)
        .clip(shape = RoundedCornerShape(50.dp))
        .clickable {
            onclick()
        }
        , contentAlignment = Alignment.Center,
    ){
        Canvas(modifier = Modifier.size(40.dp)){
            drawCircle(color= Color.White)
        }
        Image(modifier = Modifier.size(25.dp),painter = painterResource(id = R.drawable.camera), contentDescription = "Image Error")
    }
}