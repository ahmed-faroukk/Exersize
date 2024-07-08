package com.farouk.exersize.features.authentication.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farouk.exersize.base.navigation.navbar.NavBarContainer
import com.farouk.exersize.features.authentication.data.Model.codeVerfication.VerifyCodeModel
import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginModel
import com.farouk.exersize.features.authentication.domain.entity.signup.UserSignupModel
import com.farouk.exersize.features.authentication.domain.usecase.AuthUseCase
import com.farouk.exersize.features.authentication.presentation.CodeVerfication.ReSendCodeState
import com.farouk.exersize.features.authentication.presentation.CodeVerfication.VerifyCodeState
import com.farouk.exersize.features.authentication.presentation.login.LoginState
import com.farouk.exersize.features.authentication.presentation.signup.SignUpState
import com.farouk.exersize.features.inbody.presentation.steper.StepperScreen
import com.farouk.exersize.user.data.local.UserLocalDataSource
import com.farouk.exersize.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
    private val user: UserLocalDataSource,

    ) : ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState = _loginState

    private val _signUpState = mutableStateOf(SignUpState())
    val signUpState = _signUpState

    private val _codeVerifyState = mutableStateOf(VerifyCodeState())
    val codeVerifyState = _codeVerifyState

    private val _resendCodeState = mutableStateOf(ReSendCodeState())
    val resendCodeState = _resendCodeState

    private val _token: MutableState<String> = mutableStateOf("")
    val token = _token

    private val _chatId: MutableState<String> = mutableStateOf("")
    val chatId = _chatId

    private val _userId: MutableState<String> = mutableStateOf("")
    val userId = _userId


    fun createNewAccount(userSignupModel: UserSignupModel) {

        authUseCase.userSignupUseCase(userSignupModel).onEach {
            when (it) {
                is Resource.Success -> {
                    _signUpState.value = SignUpState(data = it.data)
                }

                is Resource.Loading -> {
                    _signUpState.value = SignUpState(isLoading = true)
                }

                is Resource.Error -> {
                    _signUpState.value = SignUpState(error = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun login(userLoginModel: UserLoginModel) {

        authUseCase.userLoginUseCase(userLoginModel).onEach {
            when (it) {
                is Resource.Success -> {
                    _loginState.value = LoginState(data = it.data)
                }

                is Resource.Loading -> {
                    _loginState.value = LoginState(isLoading = true)
                }

                is Resource.Error -> {
                    _loginState.value = LoginState(error = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun verifyCode(verifyCodeModel: VerifyCodeModel) {

        authUseCase.verifyCodeUseCase(verifyCodeModel).onEach {data->
            when (data) {
                is Resource.Success -> {
                    viewModelScope.launch {
                        data.data?.msg?.chat_id?.let { it1 -> user.saveChatUserId(it1) }
                        data.data?.msg?.id?.let { it1 -> user.saveUserId(it1.toString()) }
                        data.data?.msg?.token?.let { it1 -> user.saveToken(it1) }
                    }.invokeOnCompletion {
                        setloggin()
                        _codeVerifyState.value = VerifyCodeState(data = data.data )
                    }
                }

                is Resource.Loading -> {
                    _codeVerifyState.value = VerifyCodeState(isLoading = true)
                }

                is Resource.Error -> {
                    _codeVerifyState.value =
                        VerifyCodeState(error = data.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun resendCode(phone: String) {

        authUseCase.resendCodeUseCase(phone).onEach {
            when (it) {
                is Resource.Success -> {
                    _resendCodeState.value = ReSendCodeState(data = it.data)
                }

                is Resource.Loading -> {
                    _resendCodeState.value = ReSendCodeState(isLoading = true)
                }

                is Resource.Error -> {
                    _resendCodeState.value =
                        ReSendCodeState(error = it.message ?: "error not found")
                }
            }
        }.launchIn(viewModelScope)

    }

    fun navigateToHome(navigator: cafe.adriel.voyager.navigator.Navigator) {
        viewModelScope.launch {
            if (codeVerifyState.value.data?.msg?.gender?.isNotEmpty() == true)
                navigator.replaceAll(NavBarContainer())
            else navigator.replace(StepperScreen())
        }

    }

    fun userData(token: String) {
        viewModelScope.launch {
            user.saveToken(token)
        }
    }

    fun saveUserId( userId :String) {
        viewModelScope.launch {
            user.saveUserId(userId)
        }
    }
    fun saveChatId(chatId : String) {
        viewModelScope.launch {
            user.saveChatUserId(chatId)
        }
    }



    private fun setloggin() {

        viewModelScope.launch {
            user.setLoggedIn()
        }.invokeOnCompletion {
            println("set logged in is successfully")
            getLoggInState()
        }

    }

    fun getUserData() {
        viewModelScope.launch {
            user.getUserId().onEach { userId ->
                _userId.value = userId.toString()
                println("---------------------------------------------------------userid from chat vm")
                println("$userId")
            }.launchIn(viewModelScope)

        }

        viewModelScope.launch {
            user.getToken().onEach { token ->
                _token.value = token.toString()
                println("---------------------------------------------------------token from chat vm")
                println("$token")
            }.launchIn(viewModelScope)
        }


        viewModelScope.launch {
            user.getChatId().onEach { chatId ->
                _chatId.value = chatId.toString()
                println("---------------------------------------------------------chat id from chat vm")
                println("$chatId")
            }.launchIn(viewModelScope)
        }
    }

   /* fun addChatHistoryToCHatUi(){
        _getChatState.value.data?.let {
            traineeChat.addAll(it.msg)
        }
    }*/

    fun getLoggInState() {
        viewModelScope.launch {
            user.isLoggedIn().onEach {
                println("---------------------------------------------------------is logged in $it")
            }.launchIn(viewModelScope)
        }
    }


}