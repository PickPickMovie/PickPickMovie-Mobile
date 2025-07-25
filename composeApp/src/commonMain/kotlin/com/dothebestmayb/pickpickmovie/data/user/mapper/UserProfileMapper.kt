package com.dothebestmayb.pickpickmovie.data.user.mapper

import com.dothebestmayb.pickpickmovie.data.model.UserProfile
import com.dothebestmayb.pickpickmovie.data.user.remote.model.UserProfileResponseDto

internal fun UserProfileResponseDto.toDomain(): UserProfile {
    return UserProfile(
        email = email,
        nickname = nickname,
        roles = roles,
    )
}
