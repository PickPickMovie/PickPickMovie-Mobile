package com.dothebestmayb.pickpickmovie.data.user.remote.repository

import com.dothebestmayb.pickpickmovie.data.model.AuthResult
import com.dothebestmayb.pickpickmovie.data.model.UserProfile

interface UserRepository {

    suspend fun getUserProfile(): AuthResult<UserProfile>
}
