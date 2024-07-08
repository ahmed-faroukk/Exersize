package com.farouk.exersize.features.home.presentaion.Packages

import com.farouk.exersize.features.home.domain.entity.ShowPackagesResponse

data class GetPackagesState(
    val data : ShowPackagesResponse?= null,
    val isLoading : Boolean = false,
    val error : String = ""
)
