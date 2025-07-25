package com.dothebestmayb.pickpickmovie.ui.screen.home

sealed interface HomeAction {

    data object OnLogoutClick: HomeAction
}