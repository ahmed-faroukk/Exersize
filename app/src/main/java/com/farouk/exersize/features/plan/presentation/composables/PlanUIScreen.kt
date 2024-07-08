package com.mala.grad_project.Screenns.subsciptions

import CardSubscraptionRejected
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.farouk.exersize.R
import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.home.presentaion.composables.BellImage
import com.farouk.exersize.features.plan.domain.Entitiy.GetPlanResponse
import com.farouk.exersize.features.plan.presentation.PlansViewModel
import com.mala.grad_project.Screenns.Home.screen.RetryIcon

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlanUIScreen(
    getPlansResponse: GetPlanResponse, viewModel: PlansViewModel,
) {
    val context = LocalContext.current

    val openWebView = remember {
        mutableStateOf(false)
    }
     Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {

        Box(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.surface),

            ) {


            Column {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 25.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    RetryIcon {
                        viewModel.getTraineePlans()
                    }
                    BellImage(painterResource(id = R.drawable.bell))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
        when (getPlansResponse.payment_status) {
            "PENDING" -> CardOfSub(getPlansResponse)
            "ACCEPTED" -> CardSubPending(getPlansResponse) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("${BASE_URL}payment/${getPlansResponse.id}"))
                context.startActivity(intent)
            }

            "REJECTED" -> CardSubscraptionRejected(getPlansResponse)
            "UNSUBSCRIBED" -> TextUnSupscribed(getPlansResponse)
            "COMPLETED" -> ExerSizeScreen(getPlansResponse)
        }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WebViewComponent(url: String, onClose: () -> Unit) {
    var webViewClient = rememberWebViewClient(onClose)

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = this.webViewClient
                loadUrl(url)
            }
        }
    )
}

@Composable
private fun rememberWebViewClient(onClose: () -> Unit): WebViewClient {
    return remember {
        object : WebViewClient() {
            @Suppress("OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null && url.contains("success")) {
                    onClose()
                    return true
                }

                return super.shouldOverrideUrlLoading(view, url)
            }
        }
    }
}
