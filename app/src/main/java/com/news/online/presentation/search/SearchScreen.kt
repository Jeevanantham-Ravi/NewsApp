package com.news.online.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.online.domain.model.Article
import com.news.online.presentation.Dimens
import com.news.online.presentation.common.ArticlesList
import com.news.online.presentation.common.SearchFile
import com.news.online.presentation.navgraph.Route

@Composable
fun SearchScreen(state: SearchData, event: (SearchEvent) -> Unit, navigateToDetails: (Article) -> Unit) {

    Column(
        modifier = Modifier
            .padding(
                top = Dimens.mediumPadding, start = Dimens.mediumPadding, end = Dimens.mediumPadding
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchFile(
            text = state.searchQuery,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchEvent(it)) },
            onSearch = { event(SearchEvent.SearchNews) })

        Spacer(modifier = Modifier.height(Dimens.mediumPadding))

        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            ArticlesList(articles = articles, onClick = { navigateToDetails(it) })
        }
    }
}