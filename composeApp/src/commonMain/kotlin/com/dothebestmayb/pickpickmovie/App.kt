package com.dothebestmayb.pickpickmovie

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

@Composable
fun App(
    isLoggedIn: Boolean,
) {
    PickPickMovieTheme {
        val navController = rememberNavController()

        NavigationRoot(
            navController = navController,
            isLoggedIn = isLoggedIn,
        )
    }
}
