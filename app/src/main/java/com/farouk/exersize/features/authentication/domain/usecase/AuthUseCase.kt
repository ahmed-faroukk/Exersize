package com.farouk.exersize.features.authentication.domain.usecase

data class AuthUseCase (
     val userLoginUseCase: UserLoginUseCase,
     val userSignupUseCase: UserSignupUseCase ,
     val resendCodeUseCase: ResendCodeUseCase,
     val verifyCodeUseCase: VerifyCodeUseCase
)
