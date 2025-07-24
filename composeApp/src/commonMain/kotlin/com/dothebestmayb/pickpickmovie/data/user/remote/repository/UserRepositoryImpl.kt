package com.dothebestmayb.pickpickmovie.data.user.remote.repository

import com.dothebestmayb.pickpickmovie.data.model.AuthResult
import com.dothebestmayb.pickpickmovie.data.model.UserProfile
import com.dothebestmayb.pickpickmovie.data.user.mapper.toDomain
import com.dothebestmayb.pickpickmovie.data.user.remote.service.UserService

class UserRepositoryImpl(
    private val api: UserService,
): UserRepository {

    override suspend fun getUserProfile(): AuthResult<UserProfile> {
        return try {
            val userProfile = api.getUserProfile().toDomain()
            AuthResult.Authorized(userProfile)
        } catch (e: Exception) {
            AuthResult.Unauthorized()
        }
    }
}
