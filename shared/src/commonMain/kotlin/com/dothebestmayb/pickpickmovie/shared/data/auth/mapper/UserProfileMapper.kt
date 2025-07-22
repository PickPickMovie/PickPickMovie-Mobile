package com.dothebestmayb.pickpickmovie.shared.data.auth.mapper

import com.dothebestmayb.pickpickmovie.shared.data.auth.remote.model.UserProfileResponseDto
import com.dothebestmayb.pickpickmovie.shared.data.model.UserProfile

internal fun UserProfileResponseDto.toDomain(): UserProfile {
    return UserProfile(
        email = email,
        nickname = nickname,
        roles = roles,
    )
}
