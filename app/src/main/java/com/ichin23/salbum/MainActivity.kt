package com.ichin23.salbum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.ichin23.salbum.core.auth.AuthState
import com.ichin23.salbum.core.auth.AuthViewModel
import com.ichin23.salbum.navigation.AppNavigation
import com.ichin23.salbum.ui.theme.SalbumTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                // A splash screen continuará visível ENQUANTO
                // o estado for 'Loading'. Quando mudar, ela desaparecerá.
                viewModel.isLoggedIn.value is AuthState.Loading
            }
        }
        enableEdgeToEdge()
        setContent {
            SalbumTheme {
                AppNavigation()
            }
        }
    }
}
