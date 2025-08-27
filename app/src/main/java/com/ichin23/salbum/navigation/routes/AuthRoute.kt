package com.ichin23.salbum.navigation.routes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.compose.rememberNavController
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.ui.screens.Login.LoginScreen
import com.ichin23.salbum.ui.screens.Signup.SignupScreen

@Composable
fun NavGraphBuilder.authNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    navigation(
        startDestination = ScreenName.LOGIN_SCREEN,
        route = ScreenName.AUTH_ROUTE
    ){
        composable(route = ScreenName.LOGIN_SCREEN) {
            LoginScreen({navController.navigate(ScreenName.MAIN_ROUTE)})
        }
        composable(route = ScreenName.SIGNUP_SCREEN) {
            SignupScreen()
        }
    }
}