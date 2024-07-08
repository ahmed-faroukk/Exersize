package com.farouk.exersize.features.menu.profile.data.remote

import com.farouk.exersize.features.menu.profile.domain.Entity.EditeProfileResponse
import com.farouk.exersize.features.menu.profile.domain.Entity.UnsubscribeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileApiInterface {
    companion object {
        const val UPDATE_PROFILE = "api/trainee/profile/update"
        const val UNSUBSCRIBE_RESPONSE = "api/trainee/package/unsubscribe"
    }

    @POST(UPDATE_PROFILE)
    @FormUrlEncoded
    suspend fun updateProfile(
        @Part("fname") fname : RequestBody,
        @Part("lname") lname : RequestBody,
        @Part("phone") phone : RequestBody,
        @Part img : MultipartBody.Part,
        @Part("token") token : RequestBody,
    ): EditeProfileResponse

    @POST(UNSUBSCRIBE_RESPONSE)
    @FormUrlEncoded
    suspend fun unsubscribe(@Field("token") token : String) : UnsubscribeResponse
}