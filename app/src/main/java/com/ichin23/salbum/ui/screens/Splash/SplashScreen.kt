package com.ichin23.salbum.ui.screens.Splash

import android.util.Log
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ichin23.salbum.core.auth.AuthState
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.navigation.ScreenName
import com.ichin23.salbum.R

@Composable
fun SplashScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val authViewModel: AuthViewModel = hiltViewModel()

    // LaunchedEffect executa o bloco de código quando o `authState` muda
    LaunchedEffect(Unit) {
        val authState = authViewModel.checkInitialAuthState()
        Log.i("AuthChange", authState.toString())
        // Espera o authState deixar de ser Loading
        when (authState) {
            is AuthState.Authenticated -> {
                // Navega para a tela principal e limpa a splash da pilha
                navController.navigate(ScreenName.MAIN_ROUTE) {
                    popUpTo(ScreenName.SPLASH_SCREEN) { inclusive = true }
                }
            }
            is AuthState.Unauthenticated -> {
                // Navega para o fluxo de login e limpa a splash da pilha
                navController.navigate(ScreenName.AUTH_ROUTE) {
                    popUpTo(ScreenName.SPLASH_SCREEN) { inclusive = true }
                }
            }
            is AuthState.Loading -> {
                // Não faz nada, apenas continua mostrando a UI de loading
            }
        }
    }

    // A UI da Splash Screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Você pode colocar seu logo aqui
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.fone_logo),
            contentDescription = "Logo do APP",
            Modifier.fillMaxWidth(0.3f)
        )
    }
}
