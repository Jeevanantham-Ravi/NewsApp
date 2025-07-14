package com.news.online.presentation.bookmark

import com.news.online.domain.model.Article

data class BookmarkState(
    val articles : List<Article> = emptyList()
)