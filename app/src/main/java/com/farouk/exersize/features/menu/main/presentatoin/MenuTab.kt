package com.farouk.exersize.features.menu.main.presentatoin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.farouk.exersize.LocalTopNavigator
import com.farouk.exersize.R
import com.farouk.exersize.features.menu.MenuViewModel
import com.farouk.exersize.features.menu.aboutus.AboutUsScreen
import com.mala.grad_project.Screenns.Menu.MenuScreen

object MenuTab : Tab {

    override val options: TabOptions
    @Composable
    get() {
        val title = "Home"
        val icon = painterResource(R.drawable.menu)

        return remember {
            TabOptions(
                index = 4u,
                title = title,
                icon = icon
            )
        }
    }

    @Composable
    override fun Content() {
        val viewModel: MenuViewModel = getViewModel()
        val navigator = LocalTopNavigator.current

        Navigator(AboutUsScreen() , content = {
            MenuScreen(
                logoutClick = {
                              viewModel.updateUserData(navigator)
                },
                aboutUsClick = { /*TODO*/ },
                onSwitchClick = { /*TODO*/ }) {

            }
        })

        }

    }
