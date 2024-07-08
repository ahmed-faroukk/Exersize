package com.farouk.exersize.features.onBoarding

import OnBoardingPage
import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.CyberDunkers.Sla7ly.presentation.onBoarding.components.PageIndicator
import com.farouk.exersize.features.authentication.presentation.AuthStart
import com.farouk.exersize.features.onBoarding.Page.Companion.listOfPages
import com.farouk.exersize.features.onBoarding.components.RoundedBtn
import kotlinx.coroutines.launch
class OnboardingScreen : Screen {

    @Composable
    override fun Content() {
        OnBoardingScreen()
    }
    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun OnBoardingScreen(
        viewModel: OnBoardingViewModel = hiltViewModel()
    ) {
        val navigator = LocalNavigator.currentOrThrow

        val pageState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { listOfPages.size}
        )

        val scope = rememberCoroutineScope()

        println("current = ${pageState.currentPage}")


            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 5.dp),

                    horizontalArrangement = Arrangement.Center
                ) {
                    PageIndicator(
                        modifier = Modifier
                            .fillMaxWidth(),
                        pageSize = listOfPages.size,
                        selectedPage = pageState.currentPage,


                        )
                }
            HorizontalPager(state = pageState ) { index ->
                println("index = $index")
                if (pageState.currentPage == index)
                    OnBoardingPage(page = listOfPages[index] , index = index)
            }


            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                if (pageState.currentPage == 0) {

                    Spacer(modifier = Modifier.width(0.7.dp))

                    NextButton({
                        scope.launch {
                            pageState.animateScrollToPage(page = pageState.currentPage + 1 , animationSpec =  tween(
                                durationMillis = 300,
                                easing = FastOutSlowInEasing
                            ))
                        }
                    })

                } else if (pageState.currentPage == 2) {

                    BackButton({
                        scope.launch {
                            pageState.animateScrollToPage(page = pageState.currentPage - 1, animationSpec =  tween(
                                durationMillis = 300,
                                easing = FastOutSlowInEasing
                            ))
                        }
                    })

                    FinishButton({
                        // navigate
                        viewModel.saveAppEntry()
                        navigator.push(AuthStart())

                    })

                } else {

                    BackButton({
                        scope.launch {
                            pageState.animateScrollToPage(page = pageState.currentPage - 1, animationSpec =  tween(
                                durationMillis = 300,
                                easing = FastOutSlowInEasing
                            ))
                        }
                    })
                    NextButton({
                        scope.launch {
                            pageState.animateScrollToPage(page = pageState.currentPage + 1 , animationSpec =  tween(
                                durationMillis = 300,
                                easing = FastOutSlowInEasing
                            ) )
                        }
                    })

                }

            }


        }


    }

    @Composable
    fun NextButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
        RoundedBtn(onClick = onClick, text = "Next" , modifier = modifier)
    }

    @Composable
    fun FinishButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
        RoundedBtn(onClick = onClick, text = "Finish" , modifier = modifier)

    }

    @Composable
    fun BackButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
        TextButton(onClick = onClick, modifier = modifier.padding(20.dp)) {
            Text(text = "Back" , color = MaterialTheme.colorScheme.surface)
        }
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Indicator(
        pageState : PagerState
    ){
        Row(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.Center
        ) {
            PageIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 25.dp),
                pageSize = listOfPages.size,
                selectedPage = pageState.currentPage,
            )
        }
    }


}

