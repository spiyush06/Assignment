package com.vegasega.myapplication.data.api

import com.vegasega.myapplication.data.model.SchemeResponseModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("translate_a/single?")
    suspend fun googleTranslation(@Query("client") param1: String,
                                   @Query("sl") param2: String,
                                   @Query("tl") param3: String,
                                   @Query("dt") param4: String,
                                   @Query("q") param6: String): Call<List<Any>>

    @FormUrlEncoded
    @POST("live-scheme")
    suspend fun getScheme(
        @Field("user_id") id: String?,
        @Query("page") pageIndex: Int
    ): SchemeResponseModel
}