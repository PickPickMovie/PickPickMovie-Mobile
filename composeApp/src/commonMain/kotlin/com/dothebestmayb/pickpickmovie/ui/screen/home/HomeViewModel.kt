package com.dothebestmayb.pickpickmovie.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.pickpickmovie.data.storage.SessionStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sessionStorage: SessionStorage,
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnLogoutClick -> {
                viewModelScope.launch {
                    sessionStorage.clear()

                    _state.value = _state.value.copy(
                        isLogoutDone = true,
                    )
                }
            }
        }
    }
}