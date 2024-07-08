package com.farouk.exersize.features.home.domain.entity

data class MsgX(
    val BOD: String,
    val created_at: String,
    val email: String,
    val exp: Int,
    val fname: String,
    val id: Int,
    val lname: String,
    val personal_img: String,
    val phone: String,
    val ssn_img: String,
    val verify_code: String
)