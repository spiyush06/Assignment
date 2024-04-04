package com.cheezycode.quickpagingdemo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vegasega.myapplication.data.model.Data
import com.vegasega.myapplication.retrofit.SchemeAPI
import java.lang.Exception

class QuotePagingSource(private val schemeAPI: SchemeAPI) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val position = params.key ?: 1
            val response = schemeAPI.getScheme("1000",position)

            return LoadResult.Page(
                data = response.data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.meta.total_pages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}