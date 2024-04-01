package com.vegasega.myapplication.data.model

data class Data(
    val description: String,
    val end_at: String,
    val name: String,
    val scheme_id: Int,
    val scheme_image: SchemeImage,
    val status: String,
    val user_scheme_status: String
)