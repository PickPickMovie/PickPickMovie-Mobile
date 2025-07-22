package com.dothebestmayb.pickpickmovie.shared.data.storage

import com.dothebestmayb.pickpickmovie.shared.data.model.AuthToken

interface SessionStorage {
    suspend fun get(): AuthToken?
    suspend fun set(info: AuthToken?)
}
