
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.farouk.exersize.LocalTopNavigator
import com.farouk.exersize.base.Constants.BASE_URL
import com.farouk.exersize.features.authentication.presentation.components.AFLoading
import com.farouk.exersize.features.home.domain.entity.CoachByIdResponse
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.darkYellow
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.CardOfPortfolio
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.CircleCoachImage
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.LineTextViewChoachScreen
import com.mala.grad_project.Screenns.CoachScreen.Conmposble.StarRatingBarCoach
import kotlinx.coroutines.delay


@Composable
fun CoachScreenContent(
    rate : Float,showCoachResponse: CoachByIdResponse, onRequestPlanClick: () -> Unit,
) {
    val navigator = LocalTopNavigator.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            , horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            val rating by remember { mutableStateOf(1f) }

            // Header Section
           // CoachHeader()
            // Coach Info Section
            CoachInfo(
                rating = rate,
                name = showCoachResponse.msg.fname + " " + showCoachResponse.msg.lname,
                numOfClints = showCoachResponse.msg.trainees_number.toString(),
                img = showCoachResponse.msg.personal_img
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Trainee Reviews Section
            LineTextViewChoachScreen("Coach Portfolio")
            Spacer(modifier = Modifier.height(15.dp))
        }

        item {
            if (showCoachResponse.msg.portfolio.isEmpty())
                Text(text = "C.${showCoachResponse.msg.fname} hasn't added a portfolio yet." , modifier = Modifier
                    .shadow(25.dp)
                    .background(
                       darkYellow
                    )
                    .padding(50.dp), textAlign = TextAlign.Center  , color = blue1 , fontSize = 20.sp)
            else
            LazyListWithHomeItems(showCoachResponse)
        }
        item {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                BlueButton(onClick = {
                    navigator.pop()
                }, modifier = Modifier.width(150.dp), text = "back" , color = blue1)
                outlineButton(onClick = {
                    onRequestPlanClick()
                }, modifier = Modifier.width(150.dp), text = "request planning")
            }
            Spacer(modifier = Modifier.height(30.dp))
        }


    }
}

@Composable
fun BlueButton(
    onClick: () -> Unit, modifier: Modifier = Modifier,
    text: String, color: Color = MaterialTheme.colorScheme.surface
) {
    Button(
        onClick = onClick,
        modifier = modifier, shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            color
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp, textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}


@Composable
fun outlineButton(
    onClick: () -> Unit, modifier: Modifier = Modifier,
    text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            Color.White
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp, textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun CoachHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(blue1, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .padding(top = 30.dp)

    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(start = 10.dp),
            )
    }
}

@Composable
fun CoachInfo(rating: Float, name: String, numOfClints: String, img: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(blue1, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
            .padding(top = 30.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BASE_URL + img)
                    .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
                    .build()
            )
            Box {
                if (painter.state is AsyncImagePainter.State.Loading) {
                    Column(Modifier.size(100.dp) , verticalArrangement = Arrangement.SpaceEvenly , horizontalAlignment = Alignment.CenterHorizontally) {
                        AFLoading(color1 = blue1 , color2 = blue1 , color3 = blue1 , circleSize = 5.dp)
                    }
                }else CircleCoachImage(painter, size = 150)

            }

            Text(
                text = "Captain: $name",
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold ,
                color = Color.White
            )
            StarRatingBarCoach(
                maxStars = 6,
                rating = rating,
                onRatingChanged = {} ,
            )

            Text(
                text = "number of clients : $numOfClints ",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(20.dp))


        }
    }

}


@Composable
fun AutoScrollingLazyRow(
    modifier: Modifier = Modifier,
    itemCount: Int,
    content: @Composable (index: Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        while (true) {
            // Delay between scrolls
            delay(2000) // Adjust the delay as needed
            // Scroll to the next item
                // listState.animateScrollToItem((listState.firstVisibleItemIndex + 1) % itemCount)
            listState.animateScrollBy(850f , animationSpec = TweenSpec(durationMillis = 2000))
        }
    }

    LazyRow(
        state = listState,
        modifier = modifier
    ) {
        items(itemCount) { index ->
            content(index)
        }
    }
}

@Composable
fun LazyListWithHomeItems(coach: CoachByIdResponse) {
    AutoScrollingLazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(horizontal = 16.dp),
        itemCount = coach.msg.portfolio.size
    ) { index ->
        val item = coach.msg.portfolio[index]
        CardOfPortfolio(
            beforeImge = rememberAsyncImagePainter(BASE_URL + item.img_before),
            afterImage = rememberAsyncImagePainter(BASE_URL + item.img_after),
            comment = item.description
        )
    }
}