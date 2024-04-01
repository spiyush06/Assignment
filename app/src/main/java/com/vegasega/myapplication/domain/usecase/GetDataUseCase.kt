package com.vegasega.myapplication.domain.usecase

import com.vegasega.myapplication.data.model.SchemeResponseModel
import com.vegasega.myapplication.data.repository.DataRepository

class GetDataUseCase(private val repository: DataRepository, private val pageNumber: Int) {

    suspend operator fun invoke(i: Int): SchemeResponseModel {
        return repository.getData("1000", i)
    }
}