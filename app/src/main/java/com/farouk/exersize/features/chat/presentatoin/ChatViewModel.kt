package com.farouk.exersize.features.chat.presentatoin

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farouk.exersize.features.chat.domain.Entity.MsgX
import com.farouk.exersize.features.chat.domain.usecase.ChatUseCases
import com.farouk.exersize.features.chat.presentatoin.composables.Sender
import com.farouk.exersize.user.data.local.UserLocalDataSource
import com.farouk.exersize.utils.Resource
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDateTime
import javax.inject.Inject

// Shared flow for new message events
object MessageEventBus {
    private val _messageEvents = MutableSharedFlow<MsgX>()
    val messageEvents: SharedFlow<MsgX> = _messageEvents

    fun post(event: MsgX) {
        _messageEvents.tryEmit(event)
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ChatViewModel @Inject constructor(
    val chatUseCases: ChatUseCases,
    val userLocalDataSource: UserLocalDataSource,
) : ViewModel() {

   private val _traineeChat = MutableStateFlow<List<MsgX>>(emptyList())
     val traineeChat = _traineeChat.asStateFlow()

    private val _sendMsgState = mutableStateOf(SendMsgUiState())
    val sendMsgState = _sendMsgState

    val _getChatState = mutableStateOf(ChatUiState())

    private val _token: MutableState<String> = mutableStateOf("")
    val token = _token

    private val _chatId: MutableState<String> = mutableStateOf("")
    val chatId = _chatId

    private val _userId: MutableState<String> = mutableStateOf("")
    val userId = _userId

    val messageLive =  MutableLiveData<String>()


    init {
        getUserData()
        viewModelScope.launch {
            setupPusherChatting()
        }

    }

    fun sendMsg(msg: String) {
        chatUseCases.sendMsgUseCase.invoke(
            _chatId.value, msg, _token.value,
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    _sendMsgState.value = SendMsgUiState(data = it.data)
                }

                is Resource.Loading -> {
                    _sendMsgState.value = SendMsgUiState(isLoading = true)
                }

                is Resource.Error -> {
                    _sendMsgState.value = SendMsgUiState(errorMsg = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getChatHistory() {
        chatUseCases.getChatUseCase.invoke(_token.value).onEach {
            when (it) {
                is Resource.Success -> {
                    _getChatState.value = ChatUiState(data = it.data)
                    addChatHistoryToCHatUi()
                    it.data?.chat_id?.let { it1 -> saveChatId(it1) }
                }

                is Resource.Loading -> {
                    _getChatState.value = ChatUiState(isLoading = true)
                }

                is Resource.Error -> {
                    _getChatState.value = ChatUiState(errorMsg = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUserData() {
        viewModelScope.launch {
            userLocalDataSource.getUserId().onEach { userId ->
                _userId.value = userId.toString()
                println("---------------------------------------------------------userid from chat vm")
                println("$userId")
            }.launchIn(viewModelScope)

        }

        viewModelScope.launch {
            userLocalDataSource.getToken().onEach { token ->
                _token.value = token.toString()
                println("---------------------------------------------------------token from chat vm")
                println("$token")
            }.launchIn(viewModelScope)
        }


        viewModelScope.launch {
            userLocalDataSource.getChatId().onEach { chatId ->
                _chatId.value = chatId.toString()
                println("---------------------------------------------------------chat id from chat vm")
                println("$chatId")
            }.launchIn(viewModelScope)
        }
    }

    fun addChatHistoryToCHatUi(){
        _getChatState.value.data?.let {
            if (it.msg.isNotEmpty()){
                _traineeChat.value = emptyList()
                _traineeChat.value = it.msg
            }
        }
    }
    fun addMsg(msg :String){
        _traineeChat.value += MsgX(
            msg,
            Sender.TRAINEE.toString().toLowerCase(),
            LocalDateTime.now().toString()
        )
    }

    fun saveChatId(chatId : String) {
        viewModelScope.launch {
            userLocalDataSource.saveChatUserId(chatId)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupPusherChatting()  {
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
        val channel = pusher.subscribe("${chatId.value}Chatting")
            channel.bind("${chatId.value}send") { event ->
            Log.i("Pusher", "Received event with data: $event")
            val eventData = JSONObject(event.data)
            Log.i("Pusher", "Received event with data: $eventData")
            // Accessing data by key
            val sender = eventData.getString("sender")
            val msg = eventData.getString("message")
                if (sender == "coach")
                    _traineeChat.value += MsgX(
                        msg,
                        Sender.COACH.toString().toLowerCase(),
                        LocalDateTime.now().toString()
                    )

        }
    }


}