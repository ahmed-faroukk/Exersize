package com.farouk.exersize.features.reports.presentation

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.farouk.exersize.R
import com.farouk.exersize.features.chat.presentatoin.ChatViewModel
import com.farouk.exersize.theme.blue1

object ReportsTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = painterResource(R.drawable.reports)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel : ChatViewModel = getViewModel()

        Column(Modifier.fillMaxSize().background(blue1), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            WebViewScreen("https://exersize.loophole.site/chart/" ,"21")

        }
    }
}


@Composable
fun WebViewScreen(url: String, id: String) {
    val fullUrl = "$url$id"
    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(fullUrl)
        }
    }, update = { webView ->
        webView.loadUrl(fullUrl)
    })
}