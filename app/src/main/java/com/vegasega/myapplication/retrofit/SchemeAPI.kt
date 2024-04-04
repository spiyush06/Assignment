package com.vegasega.myapplication.retrofit

import com.vegasega.myapplication.data.model.SchemeResponseModel
import retrofit2.http.*

interface SchemeAPI {

    @FormUrlEncoded
    @POST("live-scheme")
    suspend fun getScheme(
        @Field("user_id") id: String?,
        @Query("page") pageIndex: Int
    ): SchemeResponseModel
}