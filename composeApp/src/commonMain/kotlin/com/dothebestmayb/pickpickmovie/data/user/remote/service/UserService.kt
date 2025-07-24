package com.dothebestmayb.pickpickmovie.data.user.remote.service

import com.dothebestmayb.pickpickmovie.data.user.remote.model.UserProfileResponseDto

interface UserService {

    suspend fun getUserProfile(): UserProfileResponseDto
}
