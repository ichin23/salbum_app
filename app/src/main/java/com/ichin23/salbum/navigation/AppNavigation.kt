package com.ichin23.salbum.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navigation
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.ui.screens.Login.LoginScreen
import com.ichin23.salbum.ui.screens.Signup.SignupScreen
import androidx.compose.runtime.getValue
import com.ichin23.salbum.core.auth.AuthState
import com.ichin23.salbum.navigation.routes.MainScreen
import com.ichin23.salbum.navigation.routes.authNavGraph
import com.ichin23.salbum.ui.screens.Splash.SplashScreen

@Composable
fun AppNavigation(authViewModel: AuthViewModel = hiltViewModel<AuthViewModel>()) {

    val navController = rememberNavController()
    val authState by authViewModel.userState.collectAsState()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    val navGraph = navController.createGraph(startDestination = ScreenName.SPLASH_SCREEN) {

        // Nova rota para a Splash Screen
        composable(route = ScreenName.SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }

        authNavGraph(navController)
        composable(route = ScreenName.MAIN_ROUTE) {
            MainScreen(navController)
        }
    }

    NavHost(
        navController = navController,
        graph = navGraph
    )


}