package com.dothebestmayb.pickpickmovie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.pickpickmovie.shared.data.storage.SessionStorage
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.repository.AuthRepository
import com.dothebestmayb.pickpickmovie.shared.data.model.AuthResult
import kotlinx.coroutines.launch

class MainViewModel(
    private val sessionStorage: SessionStorage,
    private val authRepository: AuthRepository,
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)

            if (sessionStorage.get() == null) {
                state = state.copy(isLoggedIn = false, isCheckingAuth = false)
                return@launch
            }

            when (val result = authRepository.getUserProfile()) {
                is AuthResult.Authorized -> {
                    state = state.copy(
                        isLoggedIn = true,
                        isCheckingAuth = false,
                    )
                }

                is AuthResult.Conflict<*>, is AuthResult.Unauthorized<*>, is AuthResult.UnknownError -> {
                    state = state.copy(
                        isLoggedIn = false,
                        isCheckingAuth = false,
                    )
                }
            }
        }
    }
}
