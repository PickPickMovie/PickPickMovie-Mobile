package com.dothebestmayb.pickpickmovie

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dothebestmayb.pickpickmovie.ui.screen.home.HomeScreenRoot
import com.dothebestmayb.pickpickmovie.ui.screen.login.LoginScreenRoot
import com.dothebestmayb.pickpickmovie.ui.screen.register.RegisterScreenRoot
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
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Home else Login,
    ) {
        composable<Home> {
            HomeScreenRoot(
                onLogoutSuccess = {
                    navController.navigate(Login) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Login> {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(Home)
                },
                onRegisterClick = {
                    navController.navigate(Register)
                },
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
