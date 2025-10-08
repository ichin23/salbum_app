package com.ichin23.salbum.navigation.routes

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.protobuf.Timestamp
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.core.datastore.UserStateOuterClass
import com.ichin23.salbum.core.utils.toProtoTimestamp
import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.data.api.dto.salbum.user.RefreshTokenOut
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.ui.components.BottomNavigationBar
import com.ichin23.salbum.ui.screens.Home.HomeScreen
import com.ichin23.salbum.ui.screens.Profile.ProfileScreen
import com.ichin23.salbum.ui.screens.Search.SearchScreen
import com.ichin23.salbum.ui.screens.Splash.SplashScreen
import java.time.Instant
import com.ichin23.salbum.core.datastore.UserStateOuterClass.UserState
import com.ichin23.salbum.ui.screens.FullSearch.FullSearchScreen
import com.ichin23.salbum.ui.screens.Musics.MusicsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(rootNavController: NavHostController, authViewModel: AuthViewModel, modifier: Modifier = Modifier, viewModel: MainVM = hiltViewModel()) {
    val mainNavController = rememberNavController()
    val searchFocusRequest = remember{ mutableStateOf(false) }
    val loading = viewModel.loading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.checkToken(authViewModel)
    }

    SharedTransitionLayout {

        Scaffold(
            modifier = modifier.fillMaxSize(),
            bottomBar = { if(loading.value==false) BottomNavigationBar(mainNavController, { searchFocusRequest.value = true }) }
        ) { innerPadding ->

            if(loading.value==true) SplashScreen()
            else NavHost (
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
                        {mainNavController.navigate(ScreenName.FULL_SEARCH_SCREEN)}
                    )
                }
                composable(route = ScreenName.FULL_SEARCH_SCREEN) {
                    FullSearchScreen({id -> mainNavController.navigate(ScreenName.DETAIL_ALBUM + "/${id}")})
                }
                composable(route = ScreenName.PROFILE_SCREEN) {
                    ProfileScreen(navController = mainNavController)
                }

                navigation(route = ScreenName.ALBUM_FLOW, startDestination = ScreenName.DETAIL_ALBUM+ "/{albumId}") {
                    composable(
                        route = ScreenName.DETAIL_ALBUM + "/{albumId}",
                        arguments = listOf(navArgument("albumId") { type = NavType.StringType })
                    ) {
                        AlbumDetailsRoute(
                            animatedVisibilityScope = this@composable, mainNavController
                        )
                    }
                    composable(route = ScreenName.MUSICS_SCREEN) {
                        MusicsScreen(mainNavController)
                    }
                }
            }
        }
    }
}

