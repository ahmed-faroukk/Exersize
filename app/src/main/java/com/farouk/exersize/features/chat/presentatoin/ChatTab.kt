package com.farouk.exersize.features.menu.presentatoin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.farouk.exersize.R
import com.farouk.exersize.features.authentication.presentation.components.AFLoading
import com.farouk.exersize.features.chat.presentatoin.ChatViewModel
import com.farouk.exersize.features.chat.presentatoin.composables.ChatUI

object ChatTab : Tab {

    override val options: TabOptions
    @Composable
    get() {
        val title = "Home"
        val icon = painterResource(R.drawable.chat)

        return remember {
            TabOptions(
                index = 3u,
                title = title,
                icon = icon
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel : ChatViewModel = getViewModel()
        val state = viewModel._getChatState.value
        val chatList = viewModel.traineeChat.collectAsState()


        LaunchedEffect(key1 = Unit) {
            viewModel.getChatHistory()
        }
        when {
            state.isLoading ->{
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AFLoading(
                            color1 = MaterialTheme.colorScheme.surface,
                            color2 = MaterialTheme.colorScheme.surface,
                            color3 = MaterialTheme.colorScheme.surface
                        )
                    }

                }
            state.data?.status == true ->{
                Column(modifier = Modifier.fillMaxSize()) {
                    ChatUI(viewModel , chatList.value){

                    }
                }

            }
        }

    }

}
