package com.vegasega.myapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vegasega.myapplication.paging.SchemePagingSource
import com.vegasega.myapplication.retrofit.SchemeAPI
import javax.inject.Inject

class SchemeRepository @Inject constructor(private val schemeAPI: SchemeAPI) {

    fun getQuotes() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        pagingSourceFactory = { SchemePagingSource(schemeAPI) }
    ).liveData
}