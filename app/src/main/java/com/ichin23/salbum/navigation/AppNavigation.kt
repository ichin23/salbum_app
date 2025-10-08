package com.ichin23.salbum.navigation

import android.util.Log
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ichin23.salbum.core.auth.AuthState
import com.ichin23.salbum.navigation.routes.MainScreen
import com.ichin23.salbum.navigation.routes.authNavGraph
import com.ichin23.salbum.ui.screens.Splash.SplashScreen
import kotlinx.coroutines.flow.map

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {

    val navController = rememberNavController()
    val authState by authViewModel.userState.collectAsStateWithLifecycle()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsStateWithLifecycle()

//    authViewModel.isLoggedIn.map {
//        Log.i("Change AuthState", it.toString())
//    }

    val navGraph = navController.createGraph(startDestination = if(isLoggedIn is AuthState.Loading) ScreenName.SPLASH_SCREEN else if (isLoggedIn is AuthState.Unauthenticated) ScreenName.AUTH_ROUTE else ScreenName.MAIN_ROUTE) {

        composable(ScreenName.SPLASH_SCREEN) {
            SplashScreen()
        }
        authNavGraph(navController)
        composable(route = ScreenName.MAIN_ROUTE) {
            MainScreen(navController, authViewModel)
        }
    }

    NavHost(
        navController = navController,
        graph = navGraph
    )


}