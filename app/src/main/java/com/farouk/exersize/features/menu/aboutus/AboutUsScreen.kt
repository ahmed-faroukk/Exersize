package com.farouk.exersize.features.menu.aboutus

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class AboutUsScreen : Screen {
    @Composable
    override fun Content() {
        Text(text = "about us")
    }
}