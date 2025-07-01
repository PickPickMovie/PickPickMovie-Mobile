package com.dothebestmayb.pickpickmovie.data.auth.mapper

import com.dothebestmayb.pickpickmovie.data.auth.remote.model.UserProfileResponseDto
import com.dothebestmayb.pickpickmovie.data.model.UserProfile

internal fun UserProfileResponseDto.toDomain(): UserProfile {
    return UserProfile(
        email = email,
        nickname = nickname,
        roles = roles,
    )
}
