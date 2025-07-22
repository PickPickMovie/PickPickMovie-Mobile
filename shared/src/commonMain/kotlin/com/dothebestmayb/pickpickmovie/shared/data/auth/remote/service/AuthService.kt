package com.dothebestmayb.pickpickmovie.shared.data.auth.remote.service

import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.LoginRequestDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.LoginResponseDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.RefreshRequestDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.RegisterRequestDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.RegisterResponseDto
import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.UserProfileResponseDto

interface AuthService {

    suspend fun register(request: RegisterRequestDto): RegisterResponseDto

    suspend fun login(request: LoginRequestDto): LoginResponseDto

    suspend fun refresh(request: RefreshRequestDto): LoginResponseDto

    suspend fun getUserProfile(): UserProfileResponseDto
}
