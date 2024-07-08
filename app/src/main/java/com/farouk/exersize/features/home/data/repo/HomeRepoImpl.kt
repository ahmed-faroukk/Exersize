package com.farouk.exersize.features.home.data.repo

import com.farouk.exersize.features.home.data.remote.HomeApiInterface
import com.farouk.exersize.features.home.domain.entity.CoachByIdResponse
import com.farouk.exersize.features.home.domain.entity.RequestPackageResponse
import com.farouk.exersize.features.home.domain.entity.ShowCoachesResponse
import com.farouk.exersize.features.home.domain.entity.ShowPackagesResponse
import com.farouk.exersize.features.home.domain.entity.ShowPortofolioResponse
import com.farouk.exersize.features.home.domain.repo.HomeRepo
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(
    private val api : HomeApiInterface
) : HomeRepo {
    override suspend fun getAllCoaches(token: String): ShowCoachesResponse = api.getAllCoaches(token)

    override suspend fun getCoach(id: String, token: String): CoachByIdResponse = api.getCoach(id,token)

    override suspend fun getPackages(coach_id: String, token: String): ShowPackagesResponse = api.getPackages(coach_id,token)

    override suspend fun reqPackages(
        coach_id: String,
        package_id: String,
        token: String,
    ): RequestPackageResponse = api.reqPackages( coach_id , package_id , token )

    override suspend fun getCoachPortfolio(coachId: String , token: String): ShowPortofolioResponse = api.getCoachPortfolio(coachId, token)
}