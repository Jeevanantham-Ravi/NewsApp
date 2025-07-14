package com.news.online.presentation.search

import androidx.paging.PagingData
import com.news.online.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchData(
    val searchQuery : String = "",
    val articles : Flow<PagingData<Article>>? = null
)
