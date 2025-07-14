package com.news.online.presentation.news_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.online.R
import com.news.online.domain.model.Article
import com.news.online.presentation.bookmark.BookmarkScreen
import com.news.online.presentation.bookmark.BookmarkViewModel
import com.news.online.presentation.details.DetailsEvent
import com.news.online.presentation.details.DetailsScreen
import com.news.online.presentation.details.DetailsViewModel
import com.news.online.presentation.home.HomeScreen
import com.news.online.presentation.home.HomeViewModel
import com.news.online.presentation.navgraph.Route
import com.news.online.presentation.news_navigator.components.BottomNavigationItem
import com.news.online.presentation.news_navigator.components.NewsBottomNavigation
import com.news.online.presentation.search.SearchScreen
import com.news.online.presentation.search.SearchViewModel

@Composable
fun NewsNavigator(

) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark")
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.BookmarkScreen.route -> 2
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route || backStackState?.destination?.route == Route.BookmarkScreen.route || backStackState?.destination?.route == Route.SearchScreen.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            NewsBottomNavigation(
                items = bottomNavigationItems,
                selected = selectedItem,
                onItemSelected = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController, route = Route.HomeScreen.route
                        )

                        1 -> navigateToTab(
                            navController = navController, route = Route.SearchScreen.route
                        )

                        2 -> navigateToTab(
                            navController = navController, route = Route.BookmarkScreen.route
                        )
                    }
                })
        }
    }

    ) {

        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    navigateToSearch = { navigateToTab(navController, Route.SearchScreen.route) },
                    navigateToDetails = { article ->
                        navigateToDetails(navController, article)
                    })
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchScreen(state = state, event = viewModel::onEvent) {
                    navigateToDetails(navController, article = it)
                }
            }

            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if(viewModel.sideEffect != null){
                     Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
                    ?.let {
                        DetailsScreen(article = it, event = viewModel::onEvent) {
                            navController.navigateUp()
                        }
                    }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(state) {
                    navigateToDetails(navController, it)
                }

            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {

    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true

        }
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.DetailsScreen.route)
}