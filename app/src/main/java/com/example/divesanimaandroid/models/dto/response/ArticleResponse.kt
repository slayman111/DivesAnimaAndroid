package com.example.divesanimaandroid.models.dto.response

data class ArticleResponse(
    val id: Int,
    val titleText: String,
    val titleImage: String?,
    val text: String,
    val date: String
)