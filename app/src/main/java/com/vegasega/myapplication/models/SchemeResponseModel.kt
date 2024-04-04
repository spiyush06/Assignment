package com.vegasega.myapplication.data.model

data class SchemeResponseModel(
    val `data`: List<Data>,
    val message: String,
    val meta: Meta,
    val status_code: Int,
    val success: Boolean
)