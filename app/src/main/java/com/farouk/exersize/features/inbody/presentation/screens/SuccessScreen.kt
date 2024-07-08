package com.farouk.exersize.features.inbody.presentation.screens

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.farouk.exersize.R
import com.farouk.exersize.features.inbody.presentation.InBodyViewModel
import com.farouk.exersize.theme.ExersizeTheme


class SuccessScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: InBodyViewModel = getViewModel()
        SuccessScreenContent(viewModel)
    }

    @Composable
    fun SuccessScreenContent(
         viewModel : InBodyViewModel 
    ) {

        val navigator = LocalNavigator.currentOrThrow
        GifImage()
    }


}

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    gif: Int = R.drawable.done_gif
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Column(
        modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(data = gif).apply(block = {
                    size(700)
                }).build(), imageLoader = imageLoader
            ),
            contentDescription = null,
            modifier = modifier.fillMaxWidth(),
        )
    }

}

@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    ExersizeTheme {
        Navigator(
            screen = SuccessScreen(),
            content = { navigator -> SlideTransition(navigator) })
    }
}