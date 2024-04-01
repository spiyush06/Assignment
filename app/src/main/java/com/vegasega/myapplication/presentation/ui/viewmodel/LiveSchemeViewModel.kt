package com.vegasega.myapplication.presentation.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vegasega.myapplication.data.local.RetroClient
import com.vegasega.myapplication.data.api.TranslationService
import com.vegasega.myapplication.data.model.SchemeResponseModel
import com.vegasega.myapplication.data.api.RetrofitClient
import com.vegasega.myapplication.domain.usecase.GetDataUseCase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiveSchemeViewModel(private val getDataUseCase: GetDataUseCase) : ViewModel() {

    private val _apiResponse = MutableLiveData<SchemeResponseModel>()
    val apiResponse: LiveData<SchemeResponseModel> = _apiResponse

    fun fetchData(pageNumber: Int) {
        viewModelScope.launch {
            _apiResponse.value = getDataUseCase.invoke(pageNumber)
        }
    }



    private val translationService = RetroClient.getClient().create(TranslationService::class.java)

    private val _translation = MutableLiveData<String>()
    val translation: LiveData<String> = _translation

    fun translateText(text: String) {

        Log.d("TEEEEEE", text)

        translationService.translateText("gtx", "en", "hi", "t", text).enqueue(object :
            Callback<List<Any>> {
            override fun onResponse(call: Call<List<Any>>, response: Response<List<Any>>) {
                if (response.isSuccessful && response.body() != null) {
                    val translationArray = response.body()!!
                    if (translationArray.isNotEmpty() && translationArray[0] is List<*>) {
                        val translations = translationArray[0] as List<*>
                        if (translations.isNotEmpty() && translations[0] is List<*>) {
                            val translationInfo = translations[0] as List<*>
                            if (translationInfo.isNotEmpty() && translationInfo[0] is String) {
                                val translatedText = translationInfo[0] as String
                                _translation.value = translatedText
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Any>>, t: Throwable) {
                _translation.value = "Error: ${t.message}"
            }
        })
    }
}