package com.farouk.exersize.user.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDataModel(
    val id : String? ,
    val name : String? ,
    val phone : String? ,
    val token :String ,

)
