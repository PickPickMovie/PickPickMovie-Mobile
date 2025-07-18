package com.dothebestmayb.pickpickmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.state.isCheckingAuth
            }
        }

        enableEdgeToEdge()

        setContent {
            PickPickMovieTheme {
                val navController = rememberNavController()
                if (!viewModel.state.isCheckingAuth) {
                    NavigationRoot(
                        navController = navController,
                        isLoggedIn = viewModel.state.isLoggedIn,
                    )

                }
            }
        }
    }
}
