
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farouk.exersize.features.onBoarding.Page
import com.farouk.exersize.features.onBoarding.components.OnBoardingText
import kotlinx.coroutines.delay
import org.w3c.dom.Text


@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page,
    index :Int = 0
) {
    val isVisible: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val width = remember {
        mutableStateOf(445)
    }
    val height = remember {
        mutableStateOf(400)
    }
    if (index==0){
        width.value = 450
        height.value = 394
    }else if (index==1){
        width.value = 460
        height.value = 418
    }else{
        width.value = 460
        height.value = 412

    }

    val targetValue = remember { mutableStateOf(390f) }

    println(isVisible.value)

    LaunchedEffect(isVisible.value) {
        // Hide the box for 200 milliseconds
        if (!isVisible.value) {
            delay(1)
            isVisible.value = true
        }
        delay(500)
    }

    LazyColumn(
        Modifier
            .padding(20.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {


        item {
            val size by animateFloatAsState(
                targetValue = if (!isVisible.value) 270f else targetValue.value,
                label = "", animationSpec = tween(500)
            )

            Box(
                Modifier
                    .height(440.dp)
                    .width(450.dp)
            ) {
                AnimatedVisibility(
                    modifier = Modifier
                        .fillMaxWidth(),
                    visible = isVisible.value,
                    enter = slideInHorizontally(
                        initialOffsetX = { -1000 }, animationSpec = tween(200)
                    ) + fadeIn(),
                    exit = slideOutHorizontally(
                        targetOffsetX = { 1000 }, animationSpec = tween(200)
                    ) + fadeOut(),
                ) {
                    Image(
                        painter = painterResource(id = page.background), contentDescription = null,
                        modifier
                            .size(450.dp)
                            .rotate(size)
                    )
                }
                AnimatedVisibility(
                    visible = isVisible.value,
                    enter =  fadeIn(
                        // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                        animationSpec = tween(300)
                    )
                ) {
                    Image(
                        painter = painterResource(id = page.img), contentDescription = null,
                        modifier
                            .size(size.dp)
                            .padding(top = 35.dp)
                    )

                }


            }

            OnBoardingText(text = stringResource(id = page.title) , fontWeight = FontWeight.Bold , textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(10.dp))
            OnBoardingText(text = stringResource(id = page.desc), textAlign = TextAlign.Center , sizeWithSp = 15)
        }

    }

}

