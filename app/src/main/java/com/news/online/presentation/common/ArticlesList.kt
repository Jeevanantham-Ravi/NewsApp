package com.news.online.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.news.online.domain.model.Article
import com.news.online.presentation.Dimens.extraSmallPadding2
import com.news.online.presentation.Dimens.mediumPadding

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier, articles: List<Article>, onClick: (Article) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(mediumPadding),
        contentPadding = PaddingValues(all = extraSmallPadding2)
    ) {
        items(articles.size) {
            val article = articles[it]
            ArticleCard(article = article, onClick = { onClick(article) })
        }
    }
}


@Composable
fun ArticlesList(
    modifier: Modifier = Modifier, articles: LazyPagingItems<Article>, onClick: (Article) -> Unit
) {
    val handlePagingRequest = handlingPagingResult(articles = articles)
    if (handlePagingRequest) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            contentPadding = PaddingValues(all = extraSmallPadding2)
        ) {
            items(articles.itemCount) {
                articles[it]?.let {
                    ArticleCard(article = it, onClick = { onClick(it) })
                }
            }
        }
    }
}

@Composable
fun handlingPagingResult(articles: LazyPagingItems<Article>): Boolean {
    val loadState = articles.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }
    }
}


@Composable
private fun ShimmerEffect() {

    Column(verticalArrangement = Arrangement.spacedBy(mediumPadding)) {
        repeat(10) {
            ArticleShimmerEffect(modifier = Modifier.padding(horizontal = mediumPadding))
        }
    }

}