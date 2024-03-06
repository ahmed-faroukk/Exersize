package com.farouk.exersize.features.authentication.domain.repository

import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginModel
import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginResponse
import com.farouk.exersize.features.authentication.domain.entity.signup.UserSignupModel
import com.farouk.exersize.features.authentication.domain.entity.signup.UserSignupResponse

interface AuthRepo {
    suspend fun userLogin(userLoginModel: UserLoginModel) : UserLoginResponse

    suspend fun userSignup(userSignupModel: UserSignupModel) : UserSignupResponse

}