package com.vegasega.myapplication.domain.usecase

import com.vegasega.myapplication.data.model.SchemeResponseModel
import com.vegasega.myapplication.data.repository.DataRepository
import com.vegasega.myapplication.data.repository.TranslationDataRepository
import retrofit2.Call

class GetTranslationDataUseCase(private val repository: TranslationDataRepository, private val client:String, private val sl:String, private val tl:String, private val dt:String, private val q:String) {
    suspend operator fun invoke (): Call<List<Any>> {
        return repository.getData("", "", "", "", "")
    }
}