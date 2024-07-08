package com.farouk.exersize.features.home.presentaion.HomeTab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.farouk.exersize.R
import com.farouk.exersize.features.home.presentaion.HomeViewModel
import com.mala.grad_project.Screenns.Home.screen.HomeUI

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = painterResource(R.drawable.home)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }



    @Composable
    override fun Content() {

        val viewModel: HomeViewModel = getViewModel()
            HomeUI(viewModel)
    }
}
