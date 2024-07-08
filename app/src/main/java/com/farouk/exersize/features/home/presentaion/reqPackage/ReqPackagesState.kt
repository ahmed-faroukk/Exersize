package com.farouk.exersize.features.home.presentaion.reqPackage

import com.farouk.exersize.features.home.domain.entity.RequestPackageResponse

data class ReqPackagesState(
    val data : RequestPackageResponse?= null,
    val isLoading : Boolean = false,
    val error : String = ""
)
