package com.farouk.exersize.features.inbody.presentation

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.farouk.exersize.base.navigation.navbar.NavBarContainer
import com.farouk.exersize.features.inbody.Util.MultiPartUtil
import com.farouk.exersize.features.inbody.domain.usecases.InBodyUseCase
import com.farouk.exersize.user.data.local.UserLocalDataSource
import com.farouk.exersize.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


@HiltViewModel
class InBodyViewModel @Inject constructor(
    private val inBodyUseCase: InBodyUseCase ,
    private val userLocalDataSource: UserLocalDataSource
) : ViewModel() {
    private val _token : MutableState<String> = mutableStateOf("")
    val token = _token

    private val _inBodyState = mutableStateOf(UserInBodyState())
    val inBodyState = _inBodyState

    val navNow = mutableStateOf(false)

    init {
        getToken()
    }
    fun sendInBodyData(
        gender: String,
        age: String,
        weight: String,
        tall: String,
        token: String,
        inBodyFilePath: Uri,
        imgFilePath: Uri,
        context: Context,
        navigator: Navigator
    ) {

            inBodyUseCase.invoke(
                prepareStringPart(gender),
                prepareStringPart(age),
                prepareStringPart(weight),
                prepareStringPart(tall),
                prepareStringPart(token),
                imgToMultiPartUtil(context , inBodyFilePath) ,
                imgToMultiPartUtil(context,  imgFilePath)
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        _inBodyState.value = UserInBodyState(data = it.data)
                    }

                    is Resource.Loading -> {
                        _inBodyState.value = UserInBodyState(isLoading = true)
                    }

                    is Resource.Error -> {
                        _inBodyState.value =
                            UserInBodyState(error = it.message ?: "error not found")
                    }
                }
            }.launchIn(viewModelScope)
        }


    private fun imgToMultiPartUtil(context: Context, url: Uri): MultipartBody.Part {

        return MultiPartUtil.fileToMultiPart(
            context, url,
            "img"
        )

    }

    private fun pdfToMultiPartUtil(context: Context, url: Uri): MultipartBody.Part? {
        Log.d("Debug", "URI: $url")
        if (url == Uri.EMPTY)
            return null

        return MultiPartUtil.fileToMultiPart(
            context, url,
            "inbody_pdf"
        )

    }


    private fun getRealPathFromURI(context: Context,contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? = context.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    private fun prepareStringPart(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }


    fun navigateToHome(navigator: Navigator) {
        viewModelScope.launch {
            delay(5000)
        }.invokeOnCompletion {
            navigator.replaceAll(NavBarContainer())
        }
    }

    fun navigateToSuccessScreen(navigator: Navigator) {

    }
    private fun preparePdfPart(context: Context,filePath: Uri): MultipartBody.Part {
        val realPath  = getRealPathFromURI(context = context , filePath)
        val file = realPath?.let { File(it) }
        val requestFile = file?.asRequestBody("application/pdf".toMediaTypeOrNull())
        return requestFile?.let { MultipartBody.Part.createFormData("inbody_pdf", file?.name, it) } ?: MultipartBody.Part.createFormData("","")
    }

    private fun prepareImagePart(filePath: String): MultipartBody.Part {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("img", file.name, requestFile)
    }

    fun getToken() {
        viewModelScope.launch {
            userLocalDataSource.getToken().onEach {token->
                _token.value = token.toString()
                println("---------------------------------------------------------token from home vm")
                println("$token")
            }.launchIn(viewModelScope)
        }
    }


}