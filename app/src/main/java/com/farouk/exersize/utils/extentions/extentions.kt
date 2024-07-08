package com.farouk.exersize.utils.extentions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

val file = File("path/to/pdf/file.pdf")
val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

fun convertPdfToMultiPartBody(pathName : String) : MultipartBody.Part{
    val file = File(pathName)
    val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())
    return  MultipartBody.Part.createFormData("file", file.name, requestFile)
}

