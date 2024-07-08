package com.farouk.exersize.features.authentication.data.remote

import com.farouk.exersize.features.authentication.common.Constants
import com.farouk.exersize.features.authentication.data.Model.codeVerfication.ResendCodeResponse
import com.farouk.exersize.features.authentication.data.Model.codeVerfication.VerifyCodeResponse
import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginModel
import com.farouk.exersize.features.authentication.domain.entity.login.UserLoginResponse
import com.farouk.exersize.features.authentication.domain.entity.signup.UserSignupModel
import com.farouk.exersize.features.authentication.domain.entity.signup.UserSignupResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiInterface {

    @POST(Constants.LOGIN_ENDPOINT)
    suspend fun userLogin(@Body loginModel: UserLoginModel) : UserLoginResponse

    @POST(Constants.SIGNUP_ENDPOINT)
    suspend fun userSignup(@Body signupModel: UserSignupModel) : UserSignupResponse

    @POST(Constants.RESEND_ENDPOINT)
    suspend fun resendCode(phone : String) : ResendCodeResponse

    @POST(Constants.VERIFY_ENDPOINT)
    @FormUrlEncoded
    suspend fun verifyCode(
        @Field("phone") phone: String,
        @Field("code") code: String
    ) : VerifyCodeResponse


}