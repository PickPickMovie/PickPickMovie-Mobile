package com.dothebestmayb.pickpickmovie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dothebestmayb.pickpickmovie.data.auth.local.storage.SessionStorage
import com.dothebestmayb.pickpickmovie.data.auth.remote.repository.AuthRepository
import com.dothebestmayb.pickpickmovie.data.model.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
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
