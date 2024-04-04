package com.vegasega.myapplication.data.model

data class Meta(
    val first_page: String,
    val last_page: String,
    val per_page: Int,
    val prev_page_url: Any,
    val total_items: Int,
    val total_pages: Int
)