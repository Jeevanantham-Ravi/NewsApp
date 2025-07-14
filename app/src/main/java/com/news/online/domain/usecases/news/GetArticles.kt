package com.news.online.domain.usecases.news

import com.news.online.data.local.NewsDao
import com.news.online.domain.model.Article
import kotlinx.coroutines.flow.Flow

class GetArticles(private val newsDao: NewsDao) {

    operator fun invoke() : Flow<List<Article>> = newsDao.getArticles()
}