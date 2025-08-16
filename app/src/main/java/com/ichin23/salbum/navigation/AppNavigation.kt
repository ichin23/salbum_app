package com.ichin23.salbum.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ichin23.salbum.navigation.routes.AlbumDetailsRoute
import com.ichin23.salbum.ui.components.BottomNavigationBar
import com.ichin23.salbum.ui.screens.AlbumDetails.AlbumDetailsScreen
import com.ichin23.salbum.ui.screens.Home.HomeScreen
import com.ichin23.salbum.ui.screens.Profile.ProfileScreen
import com.ichin23.salbum.ui.screens.Search.SearchScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavigation() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        val searchFocusRequest = remember{ mutableStateOf(false) }
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController, { searchFocusRequest.value=true }) }
        ) { innerPadding ->

            val navGraph = navController.createGraph(startDestination = ScreenName.HOME_SCREEN) {
                composable(route = ScreenName.HOME_SCREEN) {
                    HomeScreen(
                        animatedVisibilityScope = this@composable,) { id ->
                        navController.navigate(ScreenName.DETAIL_ALBUM + "/${id}")
                    }
                }
                composable(route = ScreenName.SEARCH_SCREEN) {
                    SearchScreen(searchFocusRequest.value, {searchFocusRequest.value=false})
                }
                composable(route = ScreenName.PROFILE_SCREEN) {
                    ProfileScreen()
                }
                composable(route = ScreenName.DETAIL_ALBUM + "/{albumId}") {
                    AlbumDetailsRoute(
                        animatedVisibilityScope = this@composable, navController)
                }
            }

            NavHost(
                navController = navController,
                graph = navGraph,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}