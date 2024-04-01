package com.vegasega.myapplication.data.api

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface TranslationService {
    @POST("translate_a/single")
     fun translateText(
        @Query("client") client: String,
        @Query("sl") sourceLanguage: String,
        @Query("tl") targetLanguage: String,
        @Query("dt") dt: String,
        @Query("q") text: String
    ): Call<List<Any>>
}