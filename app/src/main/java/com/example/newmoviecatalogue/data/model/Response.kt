package com.example.newmoviecatalogue.data.model

data class Response<T>(
    val page: Int,
    val results: List<T>,
    val total_pages: Int,
    val total_results: Int
)