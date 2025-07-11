package com.dothebestmayb.pickpickmovie.ui.screen.login

sealed interface LoginEvent {

    data object LoginSuccess : LoginEvent
    data object LoginFail : LoginEvent
    data object RegisterClick : LoginEvent
    data object LoginAlreadyInProgress : LoginEvent
}
