package com.news.online.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.news.online.R
import com.news.online.domain.model.Article
import com.news.online.presentation.Dimens.mediumPadding
import com.news.online.presentation.common.ArticlesList
import com.news.online.presentation.navgraph.Route

@Composable
fun BookmarkScreen(state: BookmarkState, navigateToDetails: (Article) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = mediumPadding, start = mediumPadding, end = mediumPadding)
    ) {

        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(R.color.text_title)
        )

        Spacer(modifier = Modifier.height(mediumPadding))

        ArticlesList(articles = state.articles) {
            navigateToDetails(it)
        }

    }

}