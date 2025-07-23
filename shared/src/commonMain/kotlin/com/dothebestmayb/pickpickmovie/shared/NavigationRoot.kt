package com.dothebestmayb.pickpickmovie.shared

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dothebestmayb.pickpickmovie.shared.core.context.PlatformContext
import com.dothebestmayb.pickpickmovie.shared.ui.screen.home.HomeScreenRoot
import com.dothebestmayb.pickpickmovie.shared.ui.screen.login.LoginScreenRoot
import com.dothebestmayb.pickpickmovie.shared.ui.screen.register.RegisterScreenRoot
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
    platformContext: PlatformContext,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Home else Login,
    ) {
        composable<Home> {
            HomeScreenRoot()
        }
        composable<Login> {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(Home)
                },
                onRegisterClick = {
                    navController.navigate(Register)
                },
                platformContext = platformContext,
            )
        }
        composable<Register> {
            RegisterScreenRoot(
                onRegisterSuccess = {
                    navController.navigate(Home)
                }
            )
        }
    }
}
