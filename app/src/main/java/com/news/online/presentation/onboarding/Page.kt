package com.news.online.presentation.onboarding

import androidx.annotation.DrawableRes
import com.news.online.R

data class Page(
    val title: String, val description: String, @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "The World's Oldest Newspaper Is Still Running!",
        description = "The “Post-och Inrikes Tidningar”, published in Sweden since 1645, is the world’s oldest newspaper still in circulation — now fully digital!",
        image = R.drawable.onboarding1
    ), Page(
        title = "In Old Days, Breaking News Used to Travel by Pigeon",
        description = "Before telegraphs and phones, news agencies used carrier pigeons to deliver urgent stories across cities and borders — fast, feathered journalism!",
        image = R.drawable.onboarding2
    ), Page(
        title = "More People Now Read News on Phones Than on TV or Print",
        description = "According to global surveys, over 65% of people consume news on smartphones, making mobile apps the primary source of information today.",
        image = R.drawable.onboarding3
    )
)