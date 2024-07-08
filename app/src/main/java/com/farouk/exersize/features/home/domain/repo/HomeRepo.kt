package com.farouk.exersize.features.home.domain.repo

import com.farouk.exersize.features.home.domain.entity.CoachByIdResponse
import com.farouk.exersize.features.home.domain.entity.RequestPackageResponse
import com.farouk.exersize.features.home.domain.entity.ShowCoachesResponse
import com.farouk.exersize.features.home.domain.entity.ShowPackagesResponse
import com.farouk.exersize.features.home.domain.entity.ShowPortofolioResponse

interface HomeRepo {
    suspend fun getAllCoaches(token : String) : ShowCoachesResponse

    suspend fun getCoach(id : String , token : String) : CoachByIdResponse

    suspend fun getPackages(coach_id : String , token : String) : ShowPackagesResponse

    suspend fun reqPackages(coach_id : String ,package_id : String, token : String) : RequestPackageResponse

    suspend fun getCoachPortfolio( coachId : String ,token : String ) : ShowPortofolioResponse

}