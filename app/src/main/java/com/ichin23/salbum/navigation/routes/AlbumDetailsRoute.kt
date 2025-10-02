package com.ichin23.salbum.navigation.routes

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.ui.screens.AlbumDetails.AlbumDetailsScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AlbumDetailsRoute(
                      animatedVisibilityScope: AnimatedVisibilityScope,navController: NavHostController) {
    val backStackEntry = navController.currentBackStackEntry
    val albumId = backStackEntry?.arguments?.getString("albumId")

    AlbumDetailsScreen(animatedVisibilityScope,albumId.toString(), navController)
}