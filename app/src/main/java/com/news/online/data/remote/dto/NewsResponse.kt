package com.news.online.data.remote.dto

import com.news.online.domain.model.Article

data class NewsResponse(
    val articles : List<Article>,
    val status : String,
    val totalResults : Int
)
