package com.dothebestmayb.pickpickmovie.data.auth.mapper

import com.dothebestmayb.pickpickmovie.data.auth.local.model.AuthEntity
import com.dothebestmayb.pickpickmovie.data.model.AuthToken

internal fun AuthToken.toEntity(): AuthEntity {
    return AuthEntity(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}

internal fun AuthEntity.toDomain(): AuthToken {
    return AuthToken(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}
