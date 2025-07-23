package com.dothebestmayb.pickpickmovie.shared

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.dothebestmayb.pickpickmovie.shared.core.context.PlatformContext
import com.dothebestmayb.pickpickmovie.shared.ui.screen.main.MainViewModel
import com.dothebestmayb.pickpickmovie.shared.ui.theme.PickPickMovieTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(
    platformContext: PlatformContext,
    viewModel: MainViewModel = koinViewModel(),
) {
    PickPickMovieTheme {
        val navController = rememberNavController()

        if (!viewModel.state.isCheckingAuth) {
            NavigationRoot(
                navController = navController,
                isLoggedIn = viewModel.state.isLoggedIn,
                platformContext = platformContext,
            )
        }
    }
}
