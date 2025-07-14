package com.news.online.domain.usecases.news

import com.news.online.data.local.NewsDao
import com.news.online.domain.model.Article

class UpsertArticle(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article) = newsDao.upsert(article)
}
