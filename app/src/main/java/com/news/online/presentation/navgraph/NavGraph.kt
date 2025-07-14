package com.news.online.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.news.online.presentation.home.HomeScreen
import com.news.online.presentation.home.HomeViewModel
import com.news.online.presentation.news_navigator.NewsNavigator
import com.news.online.presentation.onboarding.OnBoardingViewModel
import com.news.online.presentation.onboarding.OnboardingScreen

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route, startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnboardingScreen {
                    viewModel.onEvent(it)
                }
            }
        }

        navigation(
            route = Route.NewsNavigation.route, startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route) {
                NewsNavigator()

            }
        }
    }


}