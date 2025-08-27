package com.ichin23.salbum.navigation.routes

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.ui.components.BottomNavigationBar
import com.ichin23.salbum.ui.screens.Home.HomeScreen
import com.ichin23.salbum.ui.screens.Profile.ProfileScreen
import com.ichin23.salbum.ui.screens.Search.SearchScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(rootNavController: NavHostController, modifier: Modifier = Modifier) {
    val mainNavController = rememberNavController()
    val searchFocusRequest = remember{ mutableStateOf(false) }

    SharedTransitionLayout {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(mainNavController, { searchFocusRequest.value = true }) }
        ) { innerPadding ->
            NavHost (
                navController = mainNavController,
                startDestination = ScreenName.HOME_SCREEN,
                modifier = modifier.padding(innerPadding)
            ){
                composable(route = ScreenName.HOME_SCREEN) {
                    HomeScreen(
                        animatedVisibilityScope = this@composable,
                    ) { id ->
                        mainNavController.navigate(ScreenName.DETAIL_ALBUM + "/${id}")
                    }
                }

                composable(route = ScreenName.SEARCH_SCREEN) {
                    SearchScreen(
                        searchFocusRequest.value, { searchFocusRequest.value = false },
                        { id ->
                            mainNavController.navigate(ScreenName.DETAIL_ALBUM + "/${id}")
                        },
                    )
                }
                composable(route = ScreenName.PROFILE_SCREEN) {
                    ProfileScreen()
                }
                composable(route = ScreenName.DETAIL_ALBUM + "/{albumId}") {
                    AlbumDetailsRoute(
                        animatedVisibilityScope = this@composable, mainNavController
                    )
                }
            }
        }
    }
}

