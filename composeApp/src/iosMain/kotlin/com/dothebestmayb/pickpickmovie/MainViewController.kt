package com.dothebestmayb.pickpickmovie

import androidx.compose.ui.window.ComposeUIViewController
import com.dothebestmayb.pickpickmovie.ui.screen.main.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

fun MainViewController() = ComposeUIViewController {
    val viewModel: MainViewModel = koinViewModel()

    val state = viewModel.state

    if (!state.isCheckingAuth) {
        App(state.isLoggedIn)
    }
}