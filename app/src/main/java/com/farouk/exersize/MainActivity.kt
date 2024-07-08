package com.farouk.exersize

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.farouk.exersize.base.Connectivity.ConnectivityObserver
import com.farouk.exersize.base.Connectivity.ConnectivityObserver.Status
import com.farouk.exersize.base.Connectivity.NetworkConnectivityObserver
import com.farouk.exersize.base.composables.InfoDialog
import com.farouk.exersize.features.authentication.presentation.AuthViewModel
import com.farouk.exersize.features.splash.presentaiton.SplashScreen
import com.farouk.exersize.service.ExersizeService
import com.farouk.exersize.theme.ExersizeTheme
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

val LocalTopNavigator = compositionLocalOf<Navigator> { error("not init") }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   

    private lateinit var connectivityObserver: ConnectivityObserver
    private val foregroundService = ExersizeService()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            val infoDialog = remember { mutableStateOf(true) }
            val firstLost = remember { mutableStateOf(true) }
            val status = connectivityObserver.observe().collectAsState(
                initial = Status.Available
            )
            ExersizeTheme {
                // A surface container using the 'background' color from the theme
                val _chatId = remember {
                    mutableStateOf("")
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val viewModel: AuthViewModel = hiltViewModel()
                        setupPusher("62")

                    /*  if (chatViewModel.chatId.value.toString() != "" ){
                          setupPusherChatting(chatViewModel.chatId.value , chatViewModel)
                      }*/

                    if (status.value != Status.Available && infoDialog.value) {
                        InfoDialog(
                            title = "Whoops!",
                            desc = "No Internet Connection found.\n" +
                                    "Check your connection or try again.",
                            onDismiss = {
                                infoDialog.value = false
                                firstLost.value = false
                            }

                        )
                    }

                    if (status.value == Status.Available && !firstLost.value) {
                        InfoDialog(
                            title = "Back Online",
                            desc = "your connection is back again",
                            imgId = R.drawable.outline_wifi_24,
                            onDismiss = {
                                infoDialog.value = true
                                firstLost.value = true
                            }
                        )

                    }
                    LaunchedEffect(key1 = Unit) {
                        if (!infoDialog.value) {
                            lifecycleScope.launch {
                                delay(5000)
                            }.invokeOnCompletion {
                                infoDialog.value = true
                            }
                        }
                    }
                    AppNav()
                }
            }
        }
    }

    @Composable
    fun AppNav() {
        foregroundService.startService(applicationContext)
        Navigator(
            screen = SplashScreen,
            content = { navigator ->
                CompositionLocalProvider(value = LocalTopNavigator provides navigator) {
                    SlideTransition(navigator)
                }
            }
        )
    }

    fun setupPusher(id: String) {
        val options = PusherOptions()
        options.setCluster("eu");
        val pusher = Pusher("f2ab4244dfa2cd3140ce", options)
        pusher.connect(object : ConnectionEventListener {
            override fun onConnectionStateChange(
                change:
                ConnectionStateChange,
            ) {
                Log.i(
                    "Pusher",
                    "State changed from${change.previousState} to ${change.currentState}"
                )
            }

            override fun onError(
                message: String,
                code: String,
                e: Exception,
            ) {
                Log.i(
                    "Pusher",
                    "There was a problem connecting! code($code), message ($message), exception($e)"
                )
            }
        }, ConnectionState.ALL)
        val channel = pusher.subscribe("${id}notify")
        channel.bind("${id}notify") { event ->
            Log.i("Pusher", "Received event with data: $event")
            val eventData = JSONObject(event.data)
            Log.i("Pusher", "Received event with data: $eventData")
            // Accessing data by key
            val title = eventData.getString("title")
            val msg = eventData.getString("msg")
            pushNotification(title, msg)
        }
    }

    /* @RequiresApi(Build.VERSION_CODES.O)
     fun setupPusherChatting(chatId : String, viewModel: ChatViewModel) {
         val options = PusherOptions()
         options.setCluster("eu");
         val pusher = Pusher("f2ab4244dfa2cd3140ce", options)
         pusher.connect(object : ConnectionEventListener {
             override fun onConnectionStateChange(
                 change:
                 ConnectionStateChange,
             ) {
                 Log.i(
                     "Pusher",
                     "State changed from${change.previousState} to ${change.currentState}"
                 )
             }

             override fun onError(
                 message: String,
                 code: String,
                 e: Exception,
             ) {
                 Log.i(
                     "Pusher",
                     "There was a problem connecting! code($code), message ($message), exception($e)"
                 )
             }
         }, ConnectionState.ALL)
         val channel = pusher.subscribe("${chatId}Chatting")
         channel.bind("${chatId}send") { event ->
             Log.i("Pusher", "Received event with data: $event")
             val eventData = JSONObject(event.data)
             Log.i("Pusher", "Received event with data: $eventData")
             // Accessing data by key
             val sender = eventData.getString("sender")
             val msg = eventData.getString("message")
             viewModel.traineeChat.add(MsgX(msg , Sender.COACH.toString().toLowerCase() , LocalDateTime.now().toString()))
         }
     }

 */
    private fun pushNotification(title: String, msg: String) {
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.user)
            .setContentTitle(title)
            .setContentText(msg)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Use the notification manager to display the notification
        notificationManager.notify(2, notification)
    }

    companion object {
        private const val TAG = "MyForegroundService"
        private const val NOTIFICATION_CHANNEL_ID = "CATReloaded"
        private const val NOTIFICATION_CHANNEL_NAME = "running_notifications"
    }



}


