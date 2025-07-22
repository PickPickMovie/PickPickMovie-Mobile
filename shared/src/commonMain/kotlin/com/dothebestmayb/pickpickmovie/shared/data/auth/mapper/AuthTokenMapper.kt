package com.dothebestmayb.pickpickmovie.shared.data.auth.mapper

import com.dothebestmayb.pickpickmovie.shared.data.auth.local.model.AuthEntity
import com.dothebestmayb.pickpickmovie.shared.data.model.AuthToken

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
