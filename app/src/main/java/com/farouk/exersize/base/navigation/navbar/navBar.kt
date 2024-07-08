import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.farouk.exersize.features.home.presentaion.HomeTab.HomeTab
import com.farouk.exersize.features.menu.presentatoin.ChatTab
import com.farouk.exersize.features.menu.main.presentatoin.MenuTab
import com.farouk.exersize.features.plan.presentation.PlansTab
import com.farouk.exersize.features.reports.presentation.ReportsTab
import com.farouk.exersize.theme.blue1
import com.farouk.exersize.theme.darkYellow


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetupNavBar(navigator: Navigator?) {

    val topNavigator = LocalNavigator.current
    TabNavigator(HomeTab) {
        Scaffold(
            content = {
                CurrentTab()
            },
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(20)
                        )
                        .padding(10.dp)
                        .background(Color.Black)
                        .shadow(elevation = 10.dp)
                ) {
                    TabNavigationItem(HomeTab)
                    TabNavigationItem(PlansTab)
                    TabNavigationItem(ReportsTab)
                    TabNavigationItem(ChatTab)
                    TabNavigationItem(MenuTab)
                }
            },

            )

    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current
    val tint = if (tabNavigator.current == tab) blue1 else darkYellow

    BottomNavigationItem(
        modifier = Modifier.background(Color.White),
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let {
                Icon(
                    modifier = Modifier
                        .clip(RoundedCornerShape(25))
                        .background(MaterialTheme.colorScheme.primary),
                    painter = it,
                    contentDescription = tab.options.title,
                    tint = tint,
                )
            }
        },
    )

}



