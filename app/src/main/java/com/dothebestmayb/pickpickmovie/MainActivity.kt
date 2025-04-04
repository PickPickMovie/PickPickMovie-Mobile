package com.dothebestmayb.pickpickmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dothebestmayb.pickpickmovie.ui.screen.login.LoginScreenRoot
import com.dothebestmayb.pickpickmovie.ui.theme.PickPickMovieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PickPickMovieTheme {
                LoginScreenRoot()
            }
        }
    }
}
