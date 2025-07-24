package com.dothebestmayb.pickpickmovie.data.auth.remote.service

import com.dothebestmayb.pickpickmovie.data.auth.remote.model.LoginRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.LoginResponseDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RefreshRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RegisterRequestDto
import com.dothebestmayb.pickpickmovie.data.auth.remote.model.RegisterResponseDto

interface AuthService {

    suspend fun register(request: RegisterRequestDto): RegisterResponseDto

    suspend fun login(request: LoginRequestDto): LoginResponseDto

    suspend fun refresh(request: RefreshRequestDto): LoginResponseDto
}
