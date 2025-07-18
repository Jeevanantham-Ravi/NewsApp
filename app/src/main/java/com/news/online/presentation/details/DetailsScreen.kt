package com.news.online.presentation.details

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.news.online.R
import com.news.online.domain.model.Article
import com.news.online.presentation.Dimens
import com.news.online.presentation.Dimens.articleImageHeight
import com.news.online.presentation.details.components.DetailsTopBar

@Composable
fun DetailsScreen(
    article: Article, event: (DetailsEvent) -> Unit, navigateUp: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(onBrowsingClick = {
            Intent(Intent.ACTION_VIEW).also {
                it.data = article.url.toUri()
                if (it.resolveActivity(context.packageManager) != null) {
                    context.startActivity(it)
                }
            }
        }, onShareClick = {
            Intent(Intent.ACTION_SEND).also {
                it.putExtra(Intent.EXTRA_TEXT, article.url)
                it.type = "text/plain"
                if (it.resolveActivity(context.packageManager) != null) {
                    context.startActivity(it)
                }
            }
        }, onBackClick = {
            navigateUp()
        }, onBookmarkClick = {
            event(DetailsEvent.UpsertDeleteArticle(article))
        })

        LazyColumn(
            modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(
                start = Dimens.mediumPadding, end = Dimens.mediumPadding, top = Dimens.mediumPadding
            )
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context = context).data(article.urlToImage)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(articleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(Dimens.mediumPadding))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )

                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )
            }
        }
    }

}