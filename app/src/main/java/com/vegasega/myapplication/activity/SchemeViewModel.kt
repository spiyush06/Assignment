package com.vegasega.myapplication.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vegasega.myapplication.repository.SchemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SchemeViewModel @Inject constructor(private val repository: SchemeRepository) : ViewModel() {
    val list = repository.getQuotes().cachedIn(viewModelScope)
}