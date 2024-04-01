package com.vegasega.myapplication.data.repository

import android.health.connect.datatypes.units.Length
import android.util.Log
import android.widget.Toast
import com.vegasega.myapplication.data.api.ApiService
import com.vegasega.myapplication.data.model.Data
import com.vegasega.myapplication.data.model.SchemeResponseModel
import dagger.hilt.android.qualifiers.ApplicationContext

interface DataRepository {
    suspend fun getData(id : String, pagenumber : Int) : SchemeResponseModel
}

class DataRepositoryImpl(private val apiService: ApiService) : DataRepository{
    override suspend fun getData(id: String, pagenumber: Int): SchemeResponseModel {
        return apiService.getScheme(id, pagenumber)
    }
}

