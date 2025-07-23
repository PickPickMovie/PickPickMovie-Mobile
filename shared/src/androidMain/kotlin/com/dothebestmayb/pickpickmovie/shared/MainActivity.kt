package com.dothebestmayb.pickpickmovie.shared

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.dothebestmayb.pickpickmovie.shared.ui.screen.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        installSplashScreen().apply {
//            setKeepOnScreenCondition {
//                viewModel.state.isCheckingAuth
//            }
//        }

        enableEdgeToEdge()

//        setContent {
//            PickPickMovieTheme {
//                val navController = rememberNavController()
//                if (!viewModel.state.isCheckingAuth) {
//                    NavigationRoot(
//                        navController = navController,
//                        isLoggedIn = viewModel.state.isLoggedIn,
//                    )
//
//                }
//            }
//        }
    }
}