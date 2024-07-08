package com.farouk.exersize.features.home.data.remote

import com.farouk.exersize.features.home.domain.entity.CoachByIdResponse
import com.farouk.exersize.features.home.domain.entity.RequestPackageResponse
import com.farouk.exersize.features.home.domain.entity.ShowCoachesResponse
import com.farouk.exersize.features.home.domain.entity.ShowPackagesResponse
import com.farouk.exersize.features.home.domain.entity.ShowPortofolioResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HomeApiInterface {
companion object{
    const val GET_ALL_COACHES_ENDPOINT = "api/trainee/coaches"
    const val GET_COACH_ENDPOINT = "api/trainee/coach"
    const val GET_PACKAGES_ENDPOINT = "api/package/show"
    const val REQ_PACKAGES_ENDPOINT = "api/trainee/package/request"
    const val GET_PORTFOLIO_ENDPOINT = "api/coach/portfolio/showOne"

}
    @POST(GET_ALL_COACHES_ENDPOINT)
    @FormUrlEncoded
    suspend fun getAllCoaches(@Field("token") token : String) : ShowCoachesResponse

    @POST(GET_COACH_ENDPOINT)
    @FormUrlEncoded
    suspend fun getCoach(@Field("id")id : String , @Field("token")token : String) : CoachByIdResponse

    @POST(GET_PACKAGES_ENDPOINT)
    @FormUrlEncoded
    suspend fun getPackages( @Field("coach_id")coach_id : String , @Field("token") token : String) : ShowPackagesResponse

    @POST(REQ_PACKAGES_ENDPOINT)
    @FormUrlEncoded
    suspend fun reqPackages(@Field("coach_id")coach_id : String ,@Field("package_id")package_id : String,@Field("token") token : String) : RequestPackageResponse


    @POST(GET_PORTFOLIO_ENDPOINT)
    @FormUrlEncoded
    suspend fun getCoachPortfolio(@Field("id") coachId : String ,  @Field("token") token : String ) : ShowPortofolioResponse

}

