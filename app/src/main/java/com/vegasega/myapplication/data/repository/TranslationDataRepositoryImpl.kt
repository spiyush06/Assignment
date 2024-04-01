package com.vegasega.myapplication.data.repository

import com.vegasega.myapplication.data.api.ApiService
import com.vegasega.myapplication.data.model.SchemeResponseModel
import retrofit2.Call

interface TranslationDataRepository {
    suspend fun getData(client : String, sl : String, tl : String, dt : String, q : String) : Call<List<Any>>
}

class TranslationDataRepositoryImpl(private val apiService: ApiService) : TranslationDataRepository{
    override suspend fun getData(
        client: String,
        sl: String,
        tl: String,
        dt: String,
        q: String
    ): Call<List<Any>> {
        return apiService.googleTranslation(client, sl, tl, dt, q)
    }

}